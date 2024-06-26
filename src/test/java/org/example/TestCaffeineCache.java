package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.web.dao.entity.UserPurse;
import org.example.web.service.JvmCacheService;
import org.example.web.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

/**
 * @author chenxuegui
 * @since 2024/6/21
 */
@Slf4j
@SpringBootTest
public class TestCaffeineCache {
    Long uid = 5000000L;

    @Autowired
    private JvmCacheService jvmCacheService;

    @Test
    public void testCacheTime() throws Exception{
        for (int i = 0; i < 10; i++) {
            UserPurse purse = jvmCacheService.queryUserPurse(uid);
            log.info("queryUserPurseCache purse={}",purse);
            Thread.sleep(5 * 1000);
        }
    }

    @Test
    public void testCacheParallel() throws Exception{
        IntStream.range(0,20).parallel().forEach(v->{
            UserPurse purse = jvmCacheService.queryUserPurseProtect(uid);
            log.info("testCacheParallel1 purse={}",purse);
        });

        Thread.sleep(5 * 1000);
        log.info("testCacheParallel");
        IntStream.range(0,20).parallel().forEach(v->{
            UserPurse purse = jvmCacheService.queryUserPurseProtect(uid);
            log.info("testCacheParallel2 purse={}",purse);

        });
    }

}