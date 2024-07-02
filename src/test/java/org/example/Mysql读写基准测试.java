package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.web.dao.entity.SingleTable;
import org.example.web.dao.entity.User;
import org.example.web.dao.entity.UserPurse;
import org.example.web.dao.entity.UserTradeRecord;
import org.example.web.dao.mapper.SingleTableMapper;
import org.example.web.dao.mapper.UserMapper;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.dao.mapper.UserTradeRecordMapper;
import org.example.web.service.UserPurseService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.web.dao.entity.UserTradeRecord.OperateType.GOLD_DEC;

/**
 * @author chenxuegui
 * @since 2024/1/4
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations=3,time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations=3,time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class Mysql读写基准测试 {

    public static void main(String[] args) throws Exception{
        Options opt = new OptionsBuilder().include(Mysql读写基准测试.class.getSimpleName())
                .threads(8)
                .jvmArgs( "-Xms2G", "-Xmx2G")
                .build();
        new Runner(opt).run();

    }

    /**
     * 小表内存型
     * */
    //@Benchmark
    public void selectUserById(){
        User user = userMapper.selectById(11);
    }

    /**大表io型
     * */
   // @Benchmark
    public void selectUserPurseById(){
        Long uid = 1L + random.nextInt(1000000);
        UserPurse purse = userPurseMapper.selectById(uid);
    }

    /**
     * 随机update
     * */
    //@Benchmark
    public void updateGoldCost(){
        Long goldCost = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(1000000);
        userPurseMapper.decrGoldCost(uid,goldCost);
    }

    /** 顺序写 */
    //@Benchmark
    public void insertTradeRecordIncrement() {
        Long goldNum = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(1000000);
        String tradeNo = System.currentTimeMillis() + "#" + autoInc.incrementAndGet();
        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setTradeNo(tradeNo).setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        tradeRecordMapper.insert(record);
    }

    /** 顺序写 */
    @Benchmark
    public void insertSingleTableIncrement() {
        Long key2 = autoInc.incrementAndGet();
        SingleTable entity = new SingleTable();
        entity.setKey1(RandomStringUtils.randomAlphabetic(1).toLowerCase(Locale.ROOT));
        entity.setKey2(key2.intValue());
        entity.setKey3(RandomStringUtils.randomAlphabetic(1).toLowerCase(Locale.ROOT));
        entity.setKeyPart1(RandomStringUtils.randomAlphabetic(1).toLowerCase(Locale.ROOT));
        entity.setKeyPart2(RandomStringUtils.randomAlphabetic(1).toLowerCase(Locale.ROOT));
        entity.setKeyPart3(RandomStringUtils.randomAlphabetic(1).toLowerCase(Locale.ROOT));
        entity.setCommonField(RandomStringUtils.randomAlphabetic(3).toLowerCase(Locale.ROOT));
        singleTableMapper.insert(entity);
    }


    /** 随机写 */
    //@Benchmark
    public void insertTradeRecordRandom() {
        Long goldNum = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt();
        String tradeNo = System.currentTimeMillis() + "#" + autoInc.incrementAndGet();
        Date curDate = new Date();
        UserTradeRecord record = new UserTradeRecord().setId(uid).setTradeNo(tradeNo).setUid(uid).setNum(goldNum).setSourceId(1)
                .setOperateType(GOLD_DEC.getCode()).setCreateTime(curDate).setUpdateTime(curDate);
        try {
            tradeRecordMapper.insert(record);
        }catch (Exception e){

        }
    }


    /**
     * 随机update并记录流水
     * */
    //@Benchmark
    public void decrUserGold(){
        Long goldCost = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(1000000);
        String tradeNo = System.currentTimeMillis() + "#" + autoInc.incrementAndGet();
        try {
            //userPurseService.decrUserGold(uid,goldCost,tradeNo,2);
        }catch (Exception e){

        }
    }


    private Random random = new Random();
    private AtomicLong autoInc = new AtomicLong();

    private ConfigurableApplicationContext context;
    private UserMapper userMapper;
    private UserPurseMapper userPurseMapper;
    private UserPurseService userPurseService;
    private UserTradeRecordMapper tradeRecordMapper;
    private SingleTableMapper singleTableMapper;



    @Setup
    public void init() {
        // 这里的WebApplication.class是项目里的spring boot启动类
        context = SpringApplication.run(MyApplication.class);
        // 获取需要测试的bean
        this.userMapper = context.getBean(UserMapper.class);
        this.userPurseMapper = context.getBean(UserPurseMapper.class);
        this.userPurseService = context.getBean(UserPurseService.class);
        this.tradeRecordMapper = context.getBean(UserTradeRecordMapper.class);
        this.singleTableMapper = context.getBean(SingleTableMapper.class);
    }

    @TearDown
    public void down() {
        context.close();

    }

}
