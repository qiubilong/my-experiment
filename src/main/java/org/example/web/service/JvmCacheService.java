package org.example.web.service;

import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.web.dao.entity.UserPurse;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .initialCapacity(10).maximumSize(10).recordStats().removalListener(new RemovalListener(){

                @Override
                public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause cause) {
                    log.info("JvmCacheService onRemoval={},cause={}",key,cause);
                }
            }).build();


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

    /** 防止缓存击穿 */
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

    /** 批量回源 */
    public Map<Long, UserPurse> queryUsers(List<Long> uids){
        @NonNull Map<Long, UserPurse> datas = medalConfigCache.getAll(uids, missUids -> {
            log.info("queryUsers misses={}",missUids);
            Map<Long, UserPurse> users = new HashMap<>();
            for (Long missUid : missUids) {
                UserPurse userPurse = new UserPurse();
                userPurse.setUid(missUid);
                userPurse.setCreateTime(new Date());

                users.put(missUid,userPurse);
            }

            return users;
        });
        return datas;
    }

    public CacheStats getCacheStatis(){
        return medalConfigCache.stats();
    }
}
