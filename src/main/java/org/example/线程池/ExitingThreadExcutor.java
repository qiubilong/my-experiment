package org.example.线程池;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2024/2/23
 */
public class ExitingThreadExcutor {

    public static void main(String[] args) {

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        ExecutorService executorService =
                MoreExecutors.getExitingExecutorService(executor,
                        100, TimeUnit.MILLISECONDS);

        executorService.submit(() -> {
            while (true) {
            }
        });
    }
}
