package org.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.example.Mysql读写基准测试;
import org.example.web.dao.entity.User;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2025/3/28
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations=3,time = 30, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations=3,time = 30, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class InterProcessMutex基准测试 {

    InterProcessMutex oneMutex;
    CuratorFramework zookeeper;

    @Setup
    public void init() {
        System.setProperty("zookeeper.sasl.client", "false");/* ZooKeeper 客户端默认尝试 SASL（安全认证）连接，导致连接很慢*/

        zookeeper = CuratorFrameworkFactory.newClient("192.168.229.130:2181,192.168.229.132:2181", 30000, 10000,
                new ExponentialBackoffRetry(1000, 1));
        oneMutex = new InterProcessMutex(zookeeper,"/oneInterProcessMutex");

        zookeeper.start();
    }

    public static void main(String[] args) throws Exception{
        Options opt = new OptionsBuilder().include(InterProcessMutex基准测试.class.getSimpleName())
                .threads(8)
                .jvmArgs( "-Xms1G", "-Xmx1G")
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public void oneInterProcessMutex(){
        try {
            oneMutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                oneMutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Benchmark
    public void perInterProcessMutex(){
        InterProcessMutex mutex = new InterProcessMutex(zookeeper,"/perInterProcessMutex");
        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
