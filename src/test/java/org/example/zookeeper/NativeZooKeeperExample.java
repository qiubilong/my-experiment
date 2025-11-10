package org.example.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class NativeZooKeeperExample {
    private static final String CONNECT_STRING = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zooKeeper;
    private CountDownLatch connectedLatch = new CountDownLatch(1);

    // 连接 ZooKeeper
    public void connect() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    connectedLatch.countDown();
                }
            }
        });
        connectedLatch.await();
        System.out.println("Connected to ZooKeeper");
    }

    // 创建节点
    public String createNode(String path, String data) throws KeeperException, InterruptedException {
        return zooKeeper.create(path, data.getBytes(), 
                               ZooDefs.Ids.OPEN_ACL_UNSAFE,
                               CreateMode.PERSISTENT);
    }

    // 读取节点数据
    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path, false, null);
        return new String(data);
    }

    // 节点数据变化监听
    public void watchNodeData(String path) throws KeeperException, InterruptedException {
        // 注册监听器，监听节点数据变化
        byte[] data = zooKeeper.getData(path, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeDataChanged) {
                    System.out.println("节点数据发生变化: " + event.getPath());
                    try {
                        // 重新注册监听
                        watchNodeData(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, null);

        System.out.println("当前节点数据: " + new String(data));
    }

    // 设置节点数据
    public Stat setData(String path, String data) throws KeeperException, InterruptedException {
        return zooKeeper.setData(path, data.getBytes(), -1);
    }

    // 检查节点是否存在
    public boolean exists(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, true);
        return stat != null;
    }

    // 删除节点
    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, -1);
    }

    // 关闭连接
    public void close() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }

    public static void main(String[] args) throws Exception {
        NativeZooKeeperExample example = new NativeZooKeeperExample();
        
        try {
            example.connect();
            
            // 创建节点
            String path = "/example-node";
            example.createNode(path, "Hello ZooKeeper");
            System.out.println("Node created: " + path);
            
            // 读取数据
            String data = example.getData(path);
            System.out.println("Node data: " + data);
            
            // 更新数据
            example.setData(path, "Updated data");
            System.out.println("Data updated");
            
            // 检查节点是否存在
            boolean exists = example.exists(path);
            System.out.println("Node exists: " + exists);
            
            // 删除节点
            example.deleteNode(path);
            System.out.println("Node deleted");
            
        } finally {
            example.close();
        }
    }
}