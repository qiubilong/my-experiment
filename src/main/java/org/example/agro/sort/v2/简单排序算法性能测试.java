package org.example.agro.sort.v2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations=2,time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations=3,time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class 简单排序算法性能测试 {

    int[] nums;

    @Benchmark
    @Group("some")
    public void insertionSort(){
        a插入排序.insertionSort(nums);
    }

    @Benchmark
    @Group("some")
    public void selectionSort(){
        b选择排序.selectionSort(nums);
    }

    @Benchmark
    @Group("some")
    public void  bubbleSort(){
        c冒泡排序.bubbleSort(nums);
    }

    @Benchmark
    @Group("some")
    public void  bubbleSortPro(){
        c冒泡排序.bubbleSortPro(nums);
    }


    Random random = new Random();
    @Setup
    public void setUp(){
        int len  = random.nextInt(20) + 1;
        nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = random.nextInt(10000) + 1;
        }
        System.out.println("initial nums="+ Arrays.toString(nums));
    }

    public static void main(String[] args) throws Exception{
        Options opt = new OptionsBuilder().include(简单排序算法性能测试.class.getSimpleName())
                .threads(1)
                .jvmArgs( "-Xms1G", "-Xmx1G")
                .build();
        new Runner(opt).run();
    }
}
