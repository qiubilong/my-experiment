package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenxuegui
 * @since 2025/2/10
 */
@SpringBootTest
@Slf4j
public class TestRedisson {

    @Autowired
    private RedissonClient redissonClient;


    @Test
    public void 重入锁源码() throws Exception{


        Thread.sleep(3* 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doMain();
            }
        }).start();

        Thread.sleep(10* 1000);//等待获取锁成功


        new Thread(new Runnable() {
            @Override
            public void run() {
                doMain();
            }
        }).start();
        Thread.sleep(300* 1000);



        new Thread(new Runnable() {
            @Override
            public void run() {
                doMain();
            }
        }).start();
        Thread.sleep(10* 1000);


        Thread.sleep(300000* 1000);

    }

    public void  doMain(){
        String key = "testLockTime";
        RLock lock = redissonClient.getLock(key);

        lock.lock();
        try {
           log.info("获得锁");
            Thread.sleep(300* 1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            log.info("释放锁");
            lock.unlock();

        }
    }
}
