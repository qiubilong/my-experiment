package org.example;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.web.dao.entity.UserTradeRecord;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.dao.mapper.UserTradeRecordMapper;
import org.example.web.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author chenxuegui
 * @since 2024/6/20
 */
@Slf4j
@SpringBootTest
public class TestTransaction {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private  UserPurseMapper userPurseMapper;
    @Autowired
    private  UserTradeRecordMapper tradeRecordMapper;

    Long uid = 5000000L;
    Long goldNum = 10L;

    @BeforeEach
    public void prepareUserData(){
        userPurseMapper.updateGoldCost(uid,30L);
        LambdaQueryWrapper<UserTradeRecord> recordQuery = new QueryWrapper<UserTradeRecord>().lambda();
        recordQuery.eq(UserTradeRecord::getUid,uid);
        tradeRecordMapper.delete(recordQuery);
        log.info("prepareUserData");
    }

    /** 事务默认不会滚Checked Exception */
    @Test
    public void decrUserGoldLockWithException() throws Exception{
        for (int i = 0; i < 10; i++) {
            transactionService.decrUserGoldLockWithException(uid,goldNum);
        }
    }

    /** static方法事务无效*/
    @Test
    public void decrUserGoldLockWithStatic(){
        for (int i = 0; i < 10; i++) {
            TransactionService.decrUserGoldLockWithStatic(uid,goldNum);
        }
    }
    /** final方法事务无效 */
    @Test
    public void decrUserGoldLockWithFinal(){
        for (int i = 0; i < 10; i++) {
            transactionService.decrUserGoldLockWithFinal(uid,goldNum);
        }
    }

    /** 事务范围被覆盖，导致不在同一个事务，事务无法一起回滚*/
    @Test
    public void decrUserGoldLockWithDiffPropagation(){
        for (int i = 0; i < 10; i++) {
            transactionService.decrUserGoldLockWithDiffPropagation(uid,goldNum);
        }
    }


}
