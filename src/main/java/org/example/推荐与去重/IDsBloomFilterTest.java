package org.example.推荐与去重;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jodd.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class IDsBloomFilterTest {

    private static Integer BLOOM_NUM = 3;

    /** 预计插入的数据 */
    private static Integer expectedInsertions = 1024;
    /** 误判率 */
    private static Double fpp = 0.001;

    static RedisCommands<String, String> redis;
    public static void  exposedRecord(Long uid, List<Long> ids) {
        MyBloomFilter bloomFilter = null;

        String keyExposed = "exposed:"+uid;
        boolean newData = true;
        int exposedNum = 0;
        String exposedData = redis.lindex(keyExposed,0);
        if(StringUtil.isNotEmpty(exposedData)){
            String[] split = exposedData.split("#");
            if(split.length ==2){
                exposedNum = Integer.valueOf(split[0]);
                if(exposedNum + ids.size() <= expectedInsertions){
                    bloomFilter = new MyBloomFilter(expectedInsertions,fpp,split[1]);
                    newData = false;
                }

            }
        }
        if(newData){
            bloomFilter = new MyBloomFilter(expectedInsertions,fpp);
            exposedNum = 0;
        }

        for (Long id : ids) {
            bloomFilter.addLong(id);
            exposedNum ++;
        }
        String exposedDataNew = String.format("%d#%s",exposedNum,bloomFilter.serialize());
        if(!exposedDataNew.equals(exposedData)){
            if(newData){
                redis.lpush(keyExposed,exposedDataNew);
            }else {
                redis.lset(keyExposed,0,exposedDataNew);
            }
        }

        redis.ltrim(keyExposed,0,BLOOM_NUM-1);
        redis.expire(keyExposed,BLOOM_NUM * 24*3600);

        //System.out.println(exposedDataNew);

    }

    public static List<MyBloomFilter> getAllExposed(Long uid) throws Exception{
        List<MyBloomFilter> bloomFilters = new ArrayList<>(8);
        String keyExposed = "exposed:"+uid;
        List<String> list = redis.lrange(keyExposed, 0, -1);
        if (!list.isEmpty()){
            for (String exposedData : list) {
                String[] split = exposedData.split("#");
                if(split.length ==2){
                    int exposedNum = Integer.valueOf(split[0]);
                    System.out.println(exposedNum);
                    MyBloomFilter bloomFilter= new MyBloomFilter(expectedInsertions,fpp,split[1]);
                    bloomFilters.add(bloomFilter);
                }
            }
        }

        return bloomFilters;
    }

    public static boolean isExposed(Long id,List<MyBloomFilter> bloomFilters){
        for (MyBloomFilter bloomFilter : bloomFilters) {
            if(bloomFilter.testLong(id)){
                return true;
            }
        }
        return false;

    }


    public static void main(String[] args) throws Exception{

        RedisClient client = RedisClient.create("redis://127.0.0.1:6379");
        StatefulRedisConnection<String, String> connection = client.connect();
        redis = connection.sync();
        String value = redis.get("key");
        long uid = 50015430;
        long id = 100086125;
        int batchSize = 30;
        List<Long> ids = new ArrayList<>(32);
        for (int i = 0; i < expectedInsertions *4; i++) {
            ids.add(id++);
            if(ids.size()>=batchSize){
                exposedRecord(uid,ids);
                ids = new ArrayList<>(32);
            }
        }

        List<MyBloomFilter> allExposed = getAllExposed(uid);
        for (int i = 0; i < expectedInsertions *2; i+=100) {
            id--;
            boolean exposed = isExposed(id, allExposed);
            System.out.println("id="+id+","+exposed);
        }

        connection.close();
        client.shutdown();

    }
}
