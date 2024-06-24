package org.example.web.service;

import com.alibaba.fastjson.JSONObject;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.example.web.common.BizServiceException;
import org.example.web.common.ResultCode;
import org.example.web.dao.entity.UserPurse;
import org.example.web.dao.entity.UserTradeRecord;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.dao.mapper.UserTradeRecordMapper;
import org.example.web.rpc.IUserPurseService;
import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.example.web.dao.entity.UserTradeRecord.OperateType.GOLD_DEC;

/**
 * @author chenxuegui
 * @since 2024/1/4
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserPurseService implements IUserPurseService {
    private final UserPurseMapper userPurseMapper;
    private final UserTradeRecordMapper tradeRecordMapper;

    private final RedisCommands<String, String> redis;

    private final RedissonClient redissonClient;


    @Transactional(rollbackFor = Throwable.class,propagation = Propagation.REQUIRED)
    public void decrUserGoldLock(Long uid, Long goldNum,Long tradeNo) throws Exception{
        String key = "decrUserGoldLock"+uid;
        RLock lock = redissonClient.getLock(key);

        try {
            lock.lock(5, TimeUnit.SECONDS);

            log.info("decrUserGoldLockSuccess tradeNo={}",tradeNo);
            decrUserGold(uid,goldNum,tradeNo,1);
            if(tradeNo % 2 == 1){
                try {
                    Thread.sleep(7 *1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    /** 无事务方法调用事务方法，事务无效 */
    public void decrUserGoldLockInner(Long uid, Long goldNum,Long tradeNo) throws Exception {
        decrUserGoldLock(uid,goldNum,tradeNo);
    }


     /**
     * @param uid
     * @param goldNum
     * @param tradeNo 交易编号
     * @param sourceId 业务来源Id
     */
    public void decrUserGold(Long uid, Long goldNum,Long tradeNo,Integer sourceId) throws Exception{
        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(sourceId)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        log.info("decrUserGoldLockDo tradeNo={}",tradeNo);
        if(tradeNo %2 == 1){
            throw new ClassNotFoundException();
        }
        int rows = userPurseMapper.decrGoldCost(uid, goldNum);
        if(rows<=0){
            throw new BizServiceException(ResultCode.USER_PURSE_MONEY_NOT_ENOUGH_ERROR,uid+"");
        }
    }

    public UserPurse getUserPurseInfo(Long uid) {
        return userPurseMapper.selectById(uid);
    }

    @Override
    public UserPurse getUserPurseInfoFromCacheRedis(Long uid) {
        String key = "UserPurseInfo_"+ uid;
        String data = redis.get(key);
        if(StringUtils.isNotEmpty(data)){
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

    @Override
    public UserPurse getUserPurseInfoFromCacheLocal(Long uid) {
        return UserPurse.EMPTY;
    }

    @Override
    public Long incrUserPurseGoldNum(Long uid, Long num) {
        String key = "UserPurseGoldNum_"+ uid;
        return redis.incrby(key,num);
    }

    public Long generateOrderNo(){
        DateTime now = new DateTime();
        String key = "generateOrderNo";
        return Long.valueOf(now.toString("yyMMddHHmm")+""+redis.incr(key));
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public void decrUserGold(Long uid, Long goldNum){
        int rows = userPurseMapper.decrGoldCost(uid, goldNum);
        if(rows<=0){
            throw new BizServiceException(ResultCode.USER_PURSE_MONEY_NOT_ENOUGH_ERROR,uid+"");
        }
        log.info("decrUserGoldDo goldNum={}",goldNum);
    }

}
