package org.example;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.example.web.dao.entity.User;
import org.example.web.dao.entity.UserPurse;
import org.example.web.dao.mapper.UserMapper;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.service.UserPurseService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenxuegui
 * @since 2024/1/4
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations=3,time = 5, timeUnit = TimeUnit.SECONDS)
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
    @Benchmark
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
        userPurseMapper.updateGoldCost(uid,goldCost);
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
            userPurseService.decrUserGold(uid,goldCost,tradeNo,2);
        }catch (Exception e){

        }
    }


    private ConfigurableApplicationContext context;
    private UserMapper userMapper;
    private UserPurseMapper userPurseMapper;
    private UserPurseService userPurseService;

    private Random random = new Random();
    private AtomicLong autoInc = new AtomicLong();


    @Setup
    public void init() {
        // 这里的WebApplication.class是项目里的spring boot启动类
        context = SpringApplication.run(MyApplication.class);
        // 获取需要测试的bean
        this.userMapper = context.getBean(UserMapper.class);
        this.userPurseMapper = context.getBean(UserPurseMapper.class);
        this.userPurseService = context.getBean(UserPurseService.class);
    }

    @TearDown
    public void down() {
        context.close();

    }

}
