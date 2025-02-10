package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
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
    public void 重入锁源码(){

    }

    public void  doMain(){
        String key = "testLockTime";
        RLock lock = redissonClient.getLock(key);

        lock.lock();
        try {
           log.info("获得锁");
            Thread.sleep(30* 1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            log.info("释放锁");
            lock.unlock();

        }
    }
}
