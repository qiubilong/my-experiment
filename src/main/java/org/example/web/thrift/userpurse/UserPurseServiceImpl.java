package org.example.web.thrift.userpurse;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.example.web.dao.entity.UserPurse;
import org.example.web.service.UserPurseService;
import org.example.web.thrift.userpurse.api.IUserPurseService;
import org.example.web.thrift.userpurse.api.InvalidOperationException;
import org.example.web.thrift.userpurse.api.UserPurseBo;
import org.springframework.stereotype.Service;

/**
 * @author chenxuegui
 * @since 2024/1/15
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserPurseServiceImpl implements IUserPurseService.Iface {


    private final RedisCommands<String, String> redis;

    private final UserPurseService userPurseService;
    static UserPurseBo purseEmpty = new UserPurseBo();
    static {
        purseEmpty.setUid(0L);
        purseEmpty.setGoldNum(0L);
    }



    @Override
    public UserPurseBo getUserPurseInfo(long id) throws InvalidOperationException, TException {
        UserPurse info = userPurseService.getUserPurseInfo(id);
        UserPurseBo bo = new UserPurseBo();
        bo.setUid(id).setGoldNum(info.getGoldNum()).setState(info.getState());
        return bo;
    }

    @Override
    public UserPurseBo getUserPurseInfoCache(long id) throws InvalidOperationException, TException {
        UserPurse info = userPurseService.getUserPurseInfoFromCacheRedis(id);
        UserPurseBo bo = new UserPurseBo();
        bo.setUid(id).setGoldNum(info.getGoldNum()).setState(info.getState());
        return bo;
    }

    @Override
    public long incrUserPurseGoldNum(long id, long num) throws InvalidOperationException, TException {
        String key = "UserPurseGoldNum_"+ id;
        return redis.incrby(key,num);
    }

    @Override
    public UserPurseBo getUserPurseInfoLocal(long id) throws InvalidOperationException, TException {

        return purseEmpty;
    }
}
