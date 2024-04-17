package org.example.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

/**
 * @author chenxuegui
 * @since 2024/4/16
 * 循环屏障相互等待一组线程完成，后到达某个执行点
 * CountDownLatch（倒计时计数器）更多是指等待任务的完成
 * 简单来讲，CyclicBarrier维护线程数量，而CountDownLatch维护任务数量
 * https://www.baeldung.com/java-cyclicbarrier-countdownlatch
 */
public class CyclicBarrier循环屏障 {

    public static void main(String[] args) {

        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>(20));

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);

        ExecutorService es = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 20; i++) {
            es.execute(() -> {
                try {
                    System.out.println(cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    // error handling
                }
            });
        }
        es.shutdown();

        System.out.println(outputScraper);
        assertTrue(outputScraper.size() > 7);
    }
}
