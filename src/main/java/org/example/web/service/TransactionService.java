package org.example.web.service;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.web.common.BizServiceException;
import org.example.web.common.ResultCode;
import org.example.web.dao.entity.UserTradeRecord;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.dao.mapper.UserTradeRecordMapper;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.example.web.dao.entity.UserTradeRecord.OperateType.GOLD_DEC;


/**
 * @author chenxuegui
 * @since 2024/6/21
 * 事务不生效场景
 * 1、事务不开启、没配置、service没有被容器管理、存储引擎不支持
 * 2、方法内部调用，未经过事务代理
 * 3、未配置合理的回滚异常，默认不会滚Checked Exception
 * 4、final方法事务无效
 * 5、static方法事务无效
 * 6、事务范围不同，被覆盖
 * 7、方法内部调用
 * 8、try-catch异常
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService implements ApplicationContextAware {
    private  static ApplicationContext applicationContext;

    private final UserPurseMapper userPurseMapper;
    private final UserTradeRecordMapper tradeRecordMapper;

    private final RedisCommands<String, String> redis;

    private final RedissonClient redissonClient;
    private final UserPurseService userPurseService;

    /** 方法内部调用，事务无效 */
    public void decrUserGoldLockWithInner(Long uid, Long goldNum){
        decrUserGoldLockWithInner2(uid,goldNum);
    }
    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    public void decrUserGoldLockWithInner2(Long uid, Long goldNum){
        Long tradeNo = userPurseService.generateOrderNo();

        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        log.info("decrUserGoldLockDo tradeNo={}",tradeNo);

        int rows = userPurseMapper.decrGoldCost(uid, goldNum);
        if(rows<=0){
            throw new BizServiceException(ResultCode.USER_PURSE_MONEY_NOT_ENOUGH_ERROR,uid+"");
        }
    }




    /***
     * 事务默认不会滚Checked Exception
     */
    @Transactional
    public void decrUserGoldLockWithException(Long uid, Long goldNum) throws Exception{
        Long tradeNo = userPurseService.generateOrderNo();

        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(1)
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

    /***
     * static方法事务不能回滚
     */
    @Transactional(rollbackFor = Throwable.class)
    public static void decrUserGoldLockWithStatic(Long uid, Long goldNum){

        UserPurseService userPurseService = applicationContext.getBean(UserPurseService.class);
        UserPurseMapper userPurseMapper = applicationContext.getBean(UserPurseMapper.class);
        UserTradeRecordMapper tradeRecordMapper = applicationContext.getBean(UserTradeRecordMapper.class);


        Long tradeNo = userPurseService.generateOrderNo();
        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        log.info("decrUserGoldLockDo tradeNo={}",tradeNo);
        int rows = userPurseMapper.decrGoldCost(uid, goldNum);
        if(rows<=0){
            throw new BizServiceException(ResultCode.USER_PURSE_MONEY_NOT_ENOUGH_ERROR,uid+"");
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /***
     * final方法事务不能回滚
     */
    @Transactional(rollbackFor = Throwable.class)
    public  final void decrUserGoldLockWithFinal(Long uid, Long goldNum){

        UserPurseService userPurseService = applicationContext.getBean(UserPurseService.class);
        UserPurseMapper userPurseMapper = applicationContext.getBean(UserPurseMapper.class);
        UserTradeRecordMapper tradeRecordMapper = applicationContext.getBean(UserTradeRecordMapper.class);

        Long tradeNo = userPurseService.generateOrderNo();

        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        log.info("decrUserGoldLockDo tradeNo={}",tradeNo);
        int rows = userPurseMapper.decrGoldCost(uid, goldNum);
        if(rows<=0){
            throw new BizServiceException(ResultCode.USER_PURSE_MONEY_NOT_ENOUGH_ERROR,uid+"");
        }
    }

    /** 事务范围被覆盖，事务无效 */
    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    public void decrUserGoldLockWithDiffPropagation(Long uid, Long goldNum){
        Long tradeNo = userPurseService.generateOrderNo();

        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        log.info("decrUserGoldLockDo tradeNo={}",tradeNo);

        /** 1、将decrUserGold方法在同个service时是内部调用，它的事务配置无效
         *  2、两个事务范围不同，会导致事务不能一起回滚
         */
        userPurseService.decrUserGoldNew(uid,goldNum);

        if(tradeNo %2 == 1){
            throw new RuntimeException();
        }
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    public void decrUserGoldLockWithThread(Long uid, Long goldNum){
        Long tradeNo = userPurseService.generateOrderNo();

        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        log.info("decrUserGoldLockDo tradeNo={}",tradeNo);

        new Thread(() -> {
            int rows = userPurseMapper.decrGoldCost(uid, goldNum);
            if(rows<=0){
                throw new BizServiceException(ResultCode.USER_PURSE_MONEY_NOT_ENOUGH_ERROR,uid+"");
            }
        }).start();

    }


    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    public void decrUserGoldLockWithTryCatch(Long uid, Long goldNum){
        Long tradeNo = userPurseService.generateOrderNo();

        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo+"").setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        //新增交易记录
        try {
            tradeRecordMapper.insert(record);
        }catch (DuplicateKeyException e){
            throw new BizServiceException(ResultCode.REPETITIVE_OPERATION);
        }
        log.info("decrUserGoldLockDo tradeNo={}",tradeNo);

        try {
            userPurseService.decrUserGold(uid,goldNum);
        }catch (Exception e){
            log.error("decrUserGoldLockWithTryCatch error",e);
        }

    }

}
