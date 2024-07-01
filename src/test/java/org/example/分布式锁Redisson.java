package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.web.dao.entity.UserPurse;
import org.example.web.service.UserPurseService;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author chenxuegui
 * @since 2024/5/8
 */
@SpringBootTest
@Slf4j
public class 分布式锁Redisson {
    Long uid = 5000000L;
    Long goldNum = 10L;

    @Autowired
    private  RedissonClient redissonClient;

    @Autowired
    private  UserPurseService userPurseService;

    @Test
    public void testLockTime() {
        String key = "testLockTime";
        IntStream.range(1,20).parallel().forEach(v->{

            RLock lock = redissonClient.getLock(key);
            try {
                boolean tryLock = lock.tryLock(3, 5, TimeUnit.SECONDS);
                log.info("lock start v={},lock={}",v,tryLock);
                if(tryLock){
                    try {
                        Thread.sleep(v *1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("lock end v={}",v);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(lock.isHeldByCurrentThread()){
                    lock.unlock();
                }
            }

        });
    }
}
