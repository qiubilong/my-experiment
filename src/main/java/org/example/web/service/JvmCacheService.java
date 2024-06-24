package org.example.web.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.example.web.dao.entity.UserPurse;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2024/6/21
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JvmCacheService {
    private final UserPurseService userPurseService;

    private Cache<Long, UserPurse> medalConfigCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .initialCapacity(10).build();


    public UserPurse queryUserPurse(Long uid){
        UserPurse purse = medalConfigCache.getIfPresent(uid);
        if(purse!=null){
            return purse;
        }
        UserPurse userPurse = userPurseService.getUserPurseInfo(uid);
        log.info("load UserPurse={}",userPurse);
        medalConfigCache.put(uid,userPurse);
        return userPurse;
    }

    /** 防止缓存雪崩 */
    public UserPurse queryUserPurseProtect(Long uid){
        return medalConfigCache.get(uid,k->{
            UserPurse userPurse = userPurseService.getUserPurseInfo(uid);
            log.info("load UserPurse={}", userPurse);
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return userPurse;
        });
    }
}
