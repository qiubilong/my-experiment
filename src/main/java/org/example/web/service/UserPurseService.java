package org.example.web.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.web.common.BizServiceException;
import org.example.web.common.ResultCode;
import org.example.web.dao.entity.UserTradeRecord;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.dao.mapper.UserTradeRecordMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
