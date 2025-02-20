package org.example.web.service;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.web.dao.entity.Product;
import org.example.web.dao.mapper.ProductMapper;
import org.jetbrains.annotations.Nullable;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2025/2/19
 */
@Service
@AllArgsConstructor
@Slf4j
public class 热点缓存ProductService {

    private final static int EXPIRE_SEC = 24 * 60 *60;

    private final Product PRODUCT_EMPTY = new Product();

    private final RedisCommands<String, String> redis;

    private final RedissonClient redisson;

    private final ProductMapper productMapper;


    private Cache<Long, Product> productsCache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .initialCapacity(10).maximumSize(10000).recordStats().removalListener(new RemovalListener(){

                @Override
                public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause cause) {
                    log.info("ProductCacheService onRemoval={},cause={}",key,cause);
                }
            }).build();



    public void updateProduct(Product product){
        Long productId = product.getId();
        String keyLockUpdateData = "Product_LockUpdateData_"+ productId;
        RReadWriteLock updateLock = redisson.getReadWriteLock(keyLockUpdateData);
        RLock writeLock = updateLock.writeLock();
        writeLock.lock(10,TimeUnit.SECONDS);

        try {
            //dao更新数据库
            productMapper.updateProduct(product);/* 解决数据库缓存双写不一致问题，要么解锁，要么监听数据库修改流水更新 */

            productsCache.put(productId,product);
            String keyCache = keyProductCache(productId);
            redis.set(keyCache,JSONObject.toJSONString(product),new SetArgs().ex(Duration.ofSeconds(getExpireSecRandom())));//设置过期时间，实现冷热数据分离

        } finally {
            writeLock.unlock();
        }
    }

    /* 多级缓存 */
    private Product getProductsCache(Long productId){
        Product product = productsCache.getIfPresent(productId);
        if(product!=null){
            return product;
        }

        String keyCache = keyProductCache(productId);
        String data = redis.get(keyCache);
        if(StringUtils.isNotEmpty(data)){
            product = JSONObject.parseObject(data, Product.class);
            /* 命中缓存，延期过期时间 */
            int expireSec = EXPIRE_SEC;
            if(product.getId() ==null){
                expireSec = 30 * 60;//空值缓存时间少
            }else {
                productsCache.put(productId,product);
            }
            expireSec += new Random().nextInt(10) * 60 ;
            redis.expire(keyCache,Duration.ofSeconds(expireSec));
            return product;
        }
        return null;
    }


    public Product getProduct(Long productId) throws InterruptedException {
        Product productCache = getProductsCache(productId); /* 多级缓存 */
        if(productCache != null){
            return productCache;
        }

        /* 1、热点key缓存失效，加锁重建缓存，防止大量并发请求数据库 */
        Product product = PRODUCT_EMPTY;
        String keyLockGetData = "Product_LockGetData_"+ productId;
        RLock lockGetData = redisson.getLock(keyLockGetData);
        boolean tryGetDataLock = lockGetData.tryLock(3, TimeUnit.SECONDS);//防止等待时间长
        if(!tryGetDataLock){
            return null;
        }

        try {

            /* 1.1 加锁成功后，数据双重校验 */
            productCache = getProductsCache(productId);
            if(productCache != null){
                return productCache;
            }


            /* 2、加读写锁,解决数据缓存双写不一致  */
            String keyLockUpdateData = "Product_LockUpdateData_"+ productId;
            RReadWriteLock updateLock = redisson.getReadWriteLock(keyLockUpdateData);
            RLock readLock = updateLock.readLock();
            readLock.lock();

            try {
                //加载数据，并且设置到缓存
                int expireSec = EXPIRE_SEC;
                product = productMapper.getProduct(productId);

                if(product == null){
                    product = PRODUCT_EMPTY;/* 3、缓存空值，防止缓存穿透 */
                    expireSec = 30 * 60;//空值缓存时间少
                }else {
                    productsCache.put(productId,product);
                }
                expireSec += new Random().nextInt(10) * 60 ; /* 4、随机过期时间，防止大量key集中过期引起缓存雪崩 */
                String keyCache = keyProductCache(productId);
                redis.set(keyCache,JSONObject.toJSONString(product),new SetArgs().ex(Duration.ofSeconds(expireSec)));//设置过期时间，实现冷热数据分离

            } finally {
                readLock.unlock();
            }

        } finally {
            lockGetData.unlock();
        }

        return product;
    }


    private static String keyProductCache(Long productId){
        String keyCache = "Product_"+productId;
        return keyCache;
    }

    private static int getExpireSecRandom(){
        int expireSec = EXPIRE_SEC;
        expireSec += new Random().nextInt(30) * 60  ; /* 4、随机过期时间，防止大量key集中过期引起缓存雪崩 */
        return expireSec;
    }


    public void createProduct(Product product){
        productMapper.createProduct(product);
        Long productId = product.getId();

        productsCache.put(productId,product);
        String keyCache = keyProductCache(productId);
        redis.set(keyCache,JSONObject.toJSONString(product),new SetArgs().ex(Duration.ofSeconds(getExpireSecRandom())));//设置过期时间，实现冷热数据分离

    }



}
