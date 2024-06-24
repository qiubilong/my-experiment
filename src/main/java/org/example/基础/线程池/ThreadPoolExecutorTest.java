package org.example.基础.线程池;

import org.junit.Assert;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

/**
 * @author chenxuegui
 * @since 2024/4/10
 */
public class ThreadPoolExecutorTest {

    /** 线程池创建线程逻辑
     *   1、当线程数小于corePoolSize时，每次提交任务都会创建新的线程
     *   2、当队列queueCapacity已满且线程数小于maxPoolSize时创建线程池
     *   3、默认拒绝策略是抛出拒绝异常
     *
     * */
    @Test
    public void whenCorePoolSizeFiveAndMaxPoolSizeTen_thenFiveThreads() throws Exception{
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();

        int maxTask = 20;
        CountDownLatch countDownLatch = new CountDownLatch(maxTask);
        this.startThreads(taskExecutor, countDownLatch, maxTask);

        while (countDownLatch.getCount() > 0) {
            Thread.sleep(100);
        }
        System.out.println(taskExecutor.getPoolSize());
    }

    public void startThreads(ThreadPoolTaskExecutor taskExecutor, CountDownLatch countDownLatch,
                             int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            System.out.println(taskExecutor.getPoolSize());
            taskExecutor.execute(() -> {
                try {
                    Thread.sleep( ThreadLocalRandom.current().nextLong(100, 1000));
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
