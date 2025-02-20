package org.example;

import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.UUID;

/**
 * @author chenxuegui
 * @since 2025/2/17
 */
@SpringBootTest
@Slf4j
public class 一步步完善Redis分布式锁 {

    private static String lockKey = "lock";

    @Autowired
    RedisCommands<String, String> redis;

    @Autowired
    private StringRedisTemplate redisTemplate;



    /*
    *  还有不足：
    *  1、不能等待加锁 --> redisson解决
    *  2、业务未结束，锁过期  --> redisson看门狗自动续约
    *  3、redis主从切换，锁过期 -- redis是AP架构，redisson红锁（多个redis实例）也无法解决
    *         --> 3.1 使用强一致架构，如zookeeper
    *         --> 3.2 只有redis主节点
    * */
    @Test
    public void lock4() {
        /* setnx加锁、过期时间是原子操作 */
        SetArgs setArgs = new SetArgs();
        setArgs.nx();//key不存在
        setArgs.ex(Duration.ofSeconds(30));//过期时间
        String lockVal = UUID.randomUUID().toString();/* 加锁唯一标识，用于判断锁是否持有锁 */
        String lock = redis.set(lockKey, lockVal, setArgs);//SET key value [EX seconds] [PX milliseconds] [NX|XX]
        if(StringUtils.isBlank(lock)){
            return;
        }
        try {
            doTaskInner();//执行时间可能大于锁时间，导致锁失效 -->需要看门狗自动延期锁时间逻辑（锁过期时间设置的很大也可以解决大部分问题）
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 使用lua脚本，实现删除锁的原子命令，防止删除别人的锁*/
            Object eval = redis.eval("if(redis.call('get',KEYS[1]) == ARGV[1]) then\n" +
                    "  redis.call('del',KEYS[1]);\n" +
                    "  return 1;\n" +
                    "end;\n" +
                    "\n" +
                    "return nil;", ScriptOutputType.BOOLEAN, new String[]{lockKey}, lockVal);
            log.info("del lock eval={}",eval);
        }
    }

    @Test
    public void lock3() {//
        /* setnx加锁、过期时间是原子操作 */
        SetArgs setArgs = new SetArgs();
        setArgs.nx();//key不存在
        setArgs.ex(Duration.ofSeconds(30));//过期时间
        String lockVal = UUID.randomUUID().toString();/* 加锁唯一标识，用于判断锁是否持有锁 */
        String lock = redis.set(lockKey, lockVal, setArgs);//SET key value [EX seconds] [PX milliseconds] [NX|XX]
        if(StringUtils.isBlank(lock)){
            return;
        }
        try {
            doTaskInner();//执行时间可能大于锁时间
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(lockVal.equals(redis.get(lockKey))){ /* 先判断是否拥有锁，再删除，不是原子操作，本质上没解决删除其他人的锁问题 */
                //如果这里执行慢了（卡顿或者网络不佳）还是有可能删除别人的锁
                redis.del(lockKey);/* 2、如果doTaskInner执行时间过长，分布式锁已经过期，这里将会删除了其他线程获取分布式锁，然后新的线程又可以获取到分布式锁，导致锁失效  */
            }
        }
    }

    @Test
    public void lock2() {//
        /* setnx加锁、过期时间是原子操作 */
        SetArgs setArgs = new SetArgs();
        setArgs.nx();//key不存在
        setArgs.ex(Duration.ofSeconds(30));//过期时间
        String lock = redis.set(lockKey, "1", setArgs);//SET key value [EX seconds] [PX milliseconds] [NX|XX]
        if(StringUtils.isBlank(lock)){
            return;
        }
        try {
            doTaskInner();//执行时间可能大于锁时间
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redis.del(lockKey);/* 2、如果doTaskInner执行时间过长，分布式锁已经过期，这里将会删除了其他线程获取分布式锁，然后新的线程又可以获取到分布式锁，导致锁失效  */
        }
    }

    @Test
    public void lock1() {//setnx加锁和过期时间不是原子操作
        Boolean lock = redis.setnx(lockKey, "1");
        if (!lock) {
            return;
        }
        redis.expire(lockKey, 30);/* 1、如果客户端崩溃或者网络断开，设置锁过期时间失败，将导致锁永久存在，后续无法加锁 */

        try {
            doTaskInner();//执行时间可能大于锁时间
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redis.del(lockKey);/* 2、如果doTaskInner执行时间过长，分布式锁已经过期，这里将会删除了其他线程获取分布式锁，然后新的线程又可以获取到分布式锁，导致锁失效  */
        }
    }
    //模拟业务
    public void doTaskInner(){

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
