package org.example.web.controller;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.web.service.UserPurseService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2024/1/16
 */
@RestController
@RequestMapping("/JMHtest")
@RequiredArgsConstructor
@State(Scope.Benchmark)
public class JMHController {


    private OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(Duration.ofSeconds(1)).build();


    @GetMapping("/startEngine")
    public void start() throws Exception{
        Options opt = new OptionsBuilder().include(JMHController.class.getSimpleName())
                .threads(16).forks(0).resultFormat(ResultFormatType.JSON)
                .mode(Mode.Throughput).timeUnit(TimeUnit.SECONDS)
                .warmupIterations(3).warmupTime(TimeValue.seconds(5))
                .measurementIterations(5).measurementTime(TimeValue.seconds(10))
                .jvmArgs("-Xms1G", "-Xmx1G")
                .build();
        new Runner(opt).run();
    }




    //@Benchmark
    public String getUserPurseInfoFromCacheRedis(){
        Request request = new Request.Builder()
                .url("http://localhost:8080/userPurse/getUserPurseInfoFromCacheRedis?uid=200000003")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Benchmark
    public String getUserPurseInfoFromCacheLocal(){
        Request request = new Request.Builder()
                .url("http://localhost:8080/userPurse/getUserPurseInfoFromCacheLocal?uid=200000003")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
