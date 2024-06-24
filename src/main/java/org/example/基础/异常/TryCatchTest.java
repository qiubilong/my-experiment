package org.example.基础.异常;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 2)
/***
 * try-catch在循环外面和在循环里面的性能的对比
 */
public class TryCatchTest {
    // 用 @Benchmark 注解标记一个方法作为基准测试方法
    @Benchmark
    public void tryfor(Blackhole blackhole) {
        // 使用 try-catch 语句包装一个 for 循环
        try {
            for (int i = 0; i < 1000; i++) {
                // 在循环中调用 Blackhole.consume() 方法
                blackhole.consume(i);
            }
        } catch (Exception e) {
            // 捕获异常并打印堆栈跟踪信息
            e.printStackTrace();
        }
    }

    // 用 @Benchmark 注解标记另一个方法作为基准测试方法
    @Benchmark
    public void fortry(Blackhole blackhole) {
        // 使用 for 循环包装一个 try-catch 语句
        for (int i = 0; i < 1000; i++) {
            try {
                // 在 try 块中调用 Blackhole.consume() 方法
                blackhole.consume(i);
            } catch (Exception e) {
                // 捕获异常并打印堆栈跟踪信息
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(TryCatchTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
