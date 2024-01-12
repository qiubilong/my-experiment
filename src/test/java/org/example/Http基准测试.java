package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
public class Http基准测试 {
    public static void main(String[] args) throws Exception{
        Options opt = new OptionsBuilder().include(Http基准测试.class.getSimpleName())
                .threads(16)
                .jvmArgs( "-Xms2G", "-Xmx2G")
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public String httpGet(){
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

    private OkHttpClient okHttpClient;
    @Setup
    public void init() {
        okHttpClient = new OkHttpClient.Builder().readTimeout(Duration.ofSeconds(1)).build();
    }
}
