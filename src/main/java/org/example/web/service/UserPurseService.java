package org.example.web.service;

import com.alibaba.fastjson.JSONObject;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.web.common.BizServiceException;
import org.example.web.common.ResultCode;
import org.example.web.dao.entity.UserPurse;
import org.example.web.dao.entity.UserTradeRecord;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.dao.mapper.UserTradeRecordMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;

import static org.example.web.dao.entity.UserTradeRecord.OperateType.GOLD_DEC;

/**
 * @author chenxuegui
 * @since 2024/1/4
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserPurseService {
    private final UserPurseMapper userPurseMapper;
    private final UserTradeRecordMapper tradeRecordMapper;

    private final RedisCommands<String, String> redis;


     /**
     * @param uid
     * @param goldNum
     * @param tradeNo 交易编号
     * @param sourceId 业务来源Id
     */
    @Transactional(rollbackFor = Exception.class)
    public void decrUserGold(Long uid, Long goldNum,String tradeNo,Integer sourceId){
        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo).setUid(uid).setNum(goldNum).setSourceId(sourceId)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        int rows = userPurseMapper.updateGoldCost(uid, goldNum);
        if(rows<=0){
            throw new BizServiceException(ResultCode.USER_PURSE_MONEY_NOT_ENOUGH_ERROR,uid+"");
        }
    }

    public UserPurse getUserPurseInfo(Long uid) {
        return userPurseMapper.selectById(uid);
    }

    public UserPurse getUserPurseInfoFromCacheRedis(Long uid) {
        String key = "UserPurseInfo_"+ uid;
        String data = redis.get(key);
        if(StringUtils.isEmpty(data)){
            return JSONObject.parseObject(data,UserPurse.class);
        }
        UserPurse vo = userPurseMapper.selectById(uid);
        if(vo == null){
            vo = new UserPurse();
            vo.setGoldNum(0L);
        }
        redis.set(key,JSONObject.toJSONString(vo), SetArgs.Builder.ex(Duration.ofMinutes(5)));
        log.info("getUserPurseInfoFromCacheRedis uid");
        return vo;
    }

    public UserPurse getUserPurseInfoFromCacheLocal(Long uid) {
        return null;
    }


}
