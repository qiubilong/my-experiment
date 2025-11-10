/*
package org.example;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.example.web.dao.entity.UserPurse;
import org.example.web.rpc.IDubboUserPurseService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

*/
/**
 * @author chenxuegui
 * @since 2024/1/12
 *//*

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

    private IDubboUserPurseService userPurseService;

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
        referenceConfig.setInterface(IDubboUserPurseService.class);
        referenceConfig.setUrl("dubbo://127.0.0.1:20880");
        referenceConfig.setConsumer(consumerConfig);
        userPurseService = (IDubboUserPurseService) referenceConfig.get();
        UserPurse user = userPurseService.getUserPurseInfoFromCacheRedis(200000010L);
        System.out.println(user);

    }
}
*/
