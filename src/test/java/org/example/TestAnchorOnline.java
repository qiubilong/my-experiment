package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author chenxuegui
 * @since 2025/4/11
 */
@SpringBootTest
@Slf4j
public class TestAnchorOnline {

    static String keyImOnline = "anchor_im_online";
    static String pendingKey = "vistor_notice_pending";
    static int maxPending = 100;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Test
    public void getAllPending(){

        Long lastTime = 156L;
        for (int i = 0; i < 50; i++) {
            lastTime++;
            Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeByScoreWithScores(pendingKey, lastTime, Long.MAX_VALUE, 0, 20);
            log.info("getAllPending lastTime={},strings={}",lastTime,typedTuples);
            for (ZSetOperations.TypedTuple<String> typedTuple : typedTuples) {
                lastTime = typedTuple.getScore().longValue();
            }
            if(typedTuples.size()<20){
                break;
            }


        }

    }


    @Test
    public void addMaxPending(){
        redisTemplate.delete(pendingKey);

        for (int i = 0; i < 300; i++) {
            redisTemplate.opsForZSet().add(pendingKey, i+"", i);
            Long zCard = redisTemplate.opsForZSet().zCard(pendingKey);
            if(zCard >maxPending + 50){
                redisTemplate.opsForZSet().removeRange(pendingKey,0,zCard-maxPending);
                log.info("addMaxPending zCard={}",zCard);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Test
    public void clearExpireOnlineAnchors(){
        Long dateTrunc = 1744358483745L;
        redisTemplate.opsForZSet().removeRangeByScore(keyImOnline,1,dateTrunc);
        log.info("clearExpireOnlineAnchors area={},dateTrunc={}",1,dateTrunc);
    }

    @Test
    public void getAllImOnline(){
        List<Long> anchors = getAllOnlineAnchors("zh");
        log.info("getAllImOnline uid={}",anchors);
    }

    public List<Long> getAllOnlineAnchors(String area){
        List<Long> all = new ArrayList<>(1000);
        long offset = 0;
        int count = 500;
        for (int i = 0; i < 20; i++) {
            List<Long> onlineAnchors = getOnlineAnchors(area, offset,count);
            log.info("getAllOnlineAnchors area={},offset={},onlineAnchors={}",area,offset,onlineAnchors);
            if (onlineAnchors.size() < count) {
                break;
            }
            offset += count;
            offset++;

            all.addAll(onlineAnchors);
        }
        return all;
    }

    public List<Long> getOnlineAnchors(String area,Long offset,int count){
        if(count<=0){
            return Collections.EMPTY_LIST;
        }
        long endSet = offset + count;
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(keyImOnline, offset, endSet);
        if(typedTuples ==null){
            return Collections.EMPTY_LIST;
        }
        ArrayList<Long> uids = new ArrayList<>(typedTuples.size()+1);
        for (ZSetOperations.TypedTuple<String> typedTuple : typedTuples) {
            Long uid = NumberUtils.toLong(typedTuple.getValue(),0);
            if(uid >0 ){
                uids.add(uid);
            }
        }
        return uids;
    }

    @Test
    public void addImOnline(){
        for (int i = 0; i < 1600; i++) {
            redisTemplate.opsForZSet().add(keyImOnline,i+"",System.currentTimeMillis());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
