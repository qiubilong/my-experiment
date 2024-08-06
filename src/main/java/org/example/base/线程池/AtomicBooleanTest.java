package org.example.base.线程池;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanTest {

    public static void main(String[] args)  throws Exception{

        AtomicBoolean doing = new AtomicBoolean(false);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(20,20,200, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(200), new ThreadPoolExecutor.CallerRunsPolicy());

        int count = 20;
        for (int i = 0; i < count; i++) {
            int finalI = i;
            executor.submit(() -> {
                if(doing.compareAndSet(false, true)){
                    System.out.println("i can doing. t="+finalI);
                }
            });
        }
        Thread.sleep(1000);

        doing.set(false);

        for (int i = 0; i < count; i++) {
            int finalI = i;
            executor.execute(() -> {
                if(doing.compareAndSet(false, true)){
                    System.out.println("i can doing2. t="+finalI);
                }
            });
        }

        executor.shutdown();

    }
}
