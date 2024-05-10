package org.example;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author chenxuegui
 * @since 2024/5/8
 */
public class 分布式锁Redisson {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // 初始化布隆过滤器
        RedissonClient client = Redisson.create(config);

        RLock lock = client.getLock("serviceLock");

        lock.lock();

    }
}
