package org.example.web.rpc;

import org.example.web.dao.entity.UserPurse;

/**
 * @author chenxuegui
 * @since 2024/1/15
 */
public interface IDubboUserPurseService {

    UserPurse getUserPurseInfoFromCacheRedis(Long uid);

    UserPurse getUserPurseInfoFromCacheLocal(Long uid);

    Long incrUserPurseGoldNum(Long uid,Long num);
}
