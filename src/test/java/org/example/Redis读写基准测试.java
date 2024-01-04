package org.example;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.example.web.dao.entity.User;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
@Threads(16)
public class Redis读写基准测试 {



    //@Benchmark
    public void selectUserById(){
        Long uid = 1L + random.nextInt(1000000);
        String key = "room_user_purse_"+ uid;
        redis.get(key);
    }

    //@Benchmark
    public void updateGoldCost(){
        Long goldCost = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(1000000);
        String key = "room_user_purse_"+ uid;
        redis.incrby(key,-goldCost);
    }

    //@Benchmark
    public void updateGoldCostHashSet(){
        Long goldCost = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(10000000);
        String key = "room_user_purse_all";
        redis.hset(key,uid+"",goldCost+"");
    }

    //@Benchmark
    public void updateGoldCostHashGet(){
        Long goldCost = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(10000000);
        String key = "room_user_purse_all";
        redis.hget(key,uid+"");
    }

    //@Benchmark
    public void momentLikeNumSortSetAdd(){
        Long goldCost = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(10000000);
        String key = "momentLikeNum";
        redis.zadd(key,goldCost,uid+"");
    }

    @Benchmark
    public void momentLikeNumSortSetGet(){
        Long goldCost = random.nextInt(20) + 1L;
        Long uid = 1L + random.nextInt(10000000);
        String key = "momentLikeNum";
        redis.zscore(key,uid+"");
    }


    public static void main(String[] args) throws Exception{
        Options opt = new OptionsBuilder().include(Redis读写基准测试.class.getSimpleName())
                .jvmArgs( "-Xms2G", "-Xmx2G")
                .build();
        new Runner(opt).run();

    }
    private ConfigurableApplicationContext context;


    RedisCommands<String, String> redis;
    private Random random = new Random();
    @Setup
    public void init() {
        context = SpringApplication.run(MyApplication.class);

        redis = context.getBean("redisPrimary",RedisCommands.class);
    }

    @TearDown
    public void down() {
        context.close();
    }
}
