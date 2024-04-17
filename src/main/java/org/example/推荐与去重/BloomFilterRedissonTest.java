package org.example.推荐与去重;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class BloomFilterRedissonTest {

    /** 预计插入的数据 */
    private static Integer expectedInsertions = 512;
    /** 误判率 */
    private static Double fpp = 0.01;

    private static String key = "watched_ids";


    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // 初始化布隆过滤器
        RedissonClient client = Redisson.create(config);

        client.getKeys().delete(key);

        RBloomFilter<Object> bloomFilter = client.getBloomFilter(key);

        bloomFilter.tryInit(expectedInsertions, fpp);
        // 布隆过滤器增加元素
        for (Integer i = 0; i < expectedInsertions; i++) {
            bloomFilter.add(i);
        }


        // 统计元素
        int count = 0;
        for (int i = expectedInsertions; i < expectedInsertions*2; i++) {
            if (bloomFilter.contains(i)) {
                count++;
            }
        }

        System.out.println("误判次数" + count);


        for (Integer i = 0; i < expectedInsertions; i++) {
            client.getSet(key+"_Set").add(i);
        }

    }
}
