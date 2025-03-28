package org.example.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenxuegui
 * @since 2025/3/28
 */
@Slf4j
public class CuratorZookeeper分布式锁 {

   static CuratorFramework zookeeper = CuratorFrameworkFactory.newClient("192.168.229.130:2181,192.168.229.132:2181", 30000, 10000,
            new ExponentialBackoffRetry(1000, 1));

    public static void main(String[] args) {
        System.setProperty("zookeeper.sasl.client", "false");/* ZooKeeper 客户端默认尝试 SASL（安全认证）连接，导致连接很慢*/
        zookeeper.start();

        InterProcessMutex mutex = new InterProcessMutex(zookeeper,"/InterProcessMutex");

        int numThread = 5;
        for (int i = 0; i < numThread; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mutex.acquire();
                        log.info("生成订单={}",getOrderCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            mutex.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }


    }


    /** 生成订单号*/
    private static int count = 0;
    public static String getOrderCode(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return simpleDateFormat.format(new Date()) + "-" + ++count;
    }
}
