package org.example.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author chenxuegui
 * @since 2025/3/28
 */
@Slf4j
public class Zookeeper分布式锁 {

    private static final String LOCK_PATH_ROOT = "/lock";
    private static final String LOCK_PATH = "/lock/temp";

    CuratorFramework zookeeper;

    Semaphore semaphore = new Semaphore(0);

    public void initLock() throws Exception {
        System.setProperty("zookeeper.sasl.client", "false");/* ZooKeeper 客户端默认尝试 SASL（安全认证）连接，导致连接很慢*/
        zookeeper = CuratorFrameworkFactory.newClient("192.168.229.130:2181,192.168.229.132:2181", 30000, 10000,
                new ExponentialBackoffRetry(1000, 1));
        zookeeper.start();

        Stat stat = zookeeper.checkExists().forPath(LOCK_PATH_ROOT);
        if(stat ==null){
            zookeeper.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(LOCK_PATH_ROOT, "daaa".getBytes(StandardCharsets.UTF_8));
        }


        CuratorCache watcherRoot = CuratorCache.build(zookeeper, LOCK_PATH_ROOT);
        CuratorCacheListener watcherListener = CuratorCacheListener.builder()
                .forDeletes(childData -> {
                    semaphore.release(1);
                })
                .build();
        watcherRoot.listenable().addListener(watcherListener);
        watcherRoot.start();

        Thread.sleep(50);
    }

    static int total = 0;
    public static void main(String[] args) throws Exception {

        Zookeeper分布式锁 zookeeper分布式锁 = new Zookeeper分布式锁();
        zookeeper分布式锁.initLock();

        //闭锁，模拟30个并发
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    zookeeper分布式锁.lock();
                    log.info("获得锁成功");
                    try {
                        for (int j = 0; j < 5; j++) {
                            total ++;
                        }
                    }finally {
                        log.info("释放锁");
                        zookeeper分布式锁.unlock();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();

        System.out.println(total);
    }

    public void lock(){
        while (!tryLock()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean tryLock(){
        try {
            zookeeper.create().withMode(CreateMode.EPHEMERAL).forPath(LOCK_PATH, "xxx".getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception e) {
        }
        return false;
    }


    public void unlock(){
        try {
            zookeeper.delete().guaranteed().forPath(LOCK_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
