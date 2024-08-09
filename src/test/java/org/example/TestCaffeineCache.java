package org.example;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.example.web.dao.entity.UserPurse;
import org.example.web.service.JvmCacheService;
import org.example.web.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
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

    /** 测试缓存时间 */
    @Test
    public void testCacheTime() throws Exception{
        for (int i = 0; i < 10; i++) {
            UserPurse purse = jvmCacheService.queryUserPurse(uid);
            log.info("queryUserPurseCache purse={}",purse);
            Thread.sleep(5 * 1000);
        }
    }

    /** 测试并发回源 */
    @Test
    public void testCacheParallel() throws Exception{
        IntStream.range(0,20).parallel().forEach(v->{
            UserPurse purse = jvmCacheService.queryUserPurseProtect(uid);
            log.info("testCacheParallel1 purse={}",purse);
        });
    }


    @Test
    public void queryUsers() throws Exception{
        List<Long> uids = Lists.newArrayList(500L,501L,502L,503L,504L);
        Map<Long, UserPurse> datas = jvmCacheService.queryUsers(uids);
        log.info("queryUsers datas={}",datas);
        Thread.sleep(3 * 1000);


        uids.add(600L);
        uids.add(601L);
        Map<Long, UserPurse> datas1 = jvmCacheService.queryUsers(uids);
        log.info("queryUsers datas1={}",datas1);
        Thread.sleep(3 * 1000);




        uids.add(700L);
        uids.add(701L);
        uids.add(702L);
        uids.add(703L);
        uids.add(704L);
        uids.add(705L);
        Map<Long, UserPurse> datas2 = jvmCacheService.queryUsers(uids);
        log.info("queryUsers datas2={}",datas2);
        Thread.sleep(3 * 1000);

        log.info("queryUsers getCacheStatis={}",jvmCacheService.getCacheStatis());

        Thread.sleep(100 * 1000);
    }

}
