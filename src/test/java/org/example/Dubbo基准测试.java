package org.example;

import io.lettuce.core.api.sync.RedisCommands;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.dubbo.common.logger.Level;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.example.web.dao.entity.User;
import org.example.web.dao.entity.UserPurse;
import org.example.web.rpc.IUserPurseService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2024/1/12
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations=3,time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations=5,time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class Dubbo基准测试 {
    public static void main(String[] args) throws Exception{
        Options opt = new OptionsBuilder().include(Dubbo基准测试.class.getSimpleName())
                .threads(16)
                .jvmArgs( "-Xms2G", "-Xmx2G")
                .build();
        new Runner(opt).run();
    }

    //@Benchmark
    public void getUserPurseInfoFromCacheRedis(){
        Long uid = 200000003L;
        UserPurse user = userPurseService.getUserPurseInfoFromCacheRedis(uid);
    }

    //@Benchmark
    public void getUserPurseInfoFromCacheLocal(){
        Long uid = 200000003L;
        UserPurse user = userPurseService.getUserPurseInfoFromCacheLocal(uid);
    }

    @Benchmark
    public void incrUserPurseGoldNum(){
        Long uid = 200000003L;
        Long total = userPurseService.incrUserPurseGoldNum(uid, 1L);
    }

    private  IUserPurseService userPurseService;

    @Setup
    public void init() {
        System.setProperty("dubbo.application.logger","slf4j");
        ApplicationConfig applicationConfig=new ApplicationConfig("sample-consumer");
        //2、配置reference
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCorethreads(16);
        consumerConfig.setTimeout(60*1000);
        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setInterface(IUserPurseService.class);
        referenceConfig.setUrl("dubbo://127.0.0.1:20880");
        referenceConfig.setConsumer(consumerConfig);
        userPurseService = (IUserPurseService) referenceConfig.get();
        UserPurse user = userPurseService.getUserPurseInfoFromCacheRedis(200000010L);
        System.out.println(user);

    }
}
