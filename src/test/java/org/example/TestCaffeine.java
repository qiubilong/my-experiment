package org.example;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2025/2/20
 */
public class TestCaffeine {

    private static int NUM = 0;
    @Test
    public void refreshAfterWriteTest() throws InterruptedException {
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS)//被动刷新，取旧值
                //.expireAfterAccess(10, TimeUnit.SECONDS)//有访问就延期
                //.expireAfterWrite(1, TimeUnit.SECONDS)//固定过期时间
                //模拟获取数据，每次获取就自增1
                .build(integer -> ++NUM);
        //获取ID=1的值，由于缓存里还没有，所以会自动放入缓存
        System.out.println(cache.get(1));// 1
        // 延迟2秒后，理论上自动刷新缓存后取到的值是2
        // 但其实不是，值还是1，因为refreshAfterWrite并不是设置了n秒后重新获取就会自动刷新
        // 而是x秒后&&第二次调用getIfPresent的时候才会被动刷新
        Thread.sleep(2000);
        //Thread.sleep(10000);
        System.out.println(cache.getIfPresent(1));// 1  -- 取旧值
        //此时才会刷新缓存，而第一次拿到的还是旧值
        System.out.println(cache.getIfPresent(1));// 2
    }
}
