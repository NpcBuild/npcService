package com.npc.cache.service.impl;

import com.npc.cache.service.CacheService;
import com.npc.redis.utils.RedisPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.TimeUnit;
/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2025/10/26 13:34
 **/

@Slf4j
@Service
@ConditionalOnProperty(name = "app.cache.type", havingValue = "redis-cluster")
public class RedisClusterCacheServiceImpl implements CacheService {

    @Value("${app.cache.type}")
    private String cacheType;

    @Override
    public void set(String key, Object value) {
        JedisCluster jedis = RedisPool.getJedis();
        jedis.set(key, value.toString());
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        JedisCluster jedis = RedisPool.getJedis();
        jedis.setex(key, (int) unit.toSeconds(timeout), value.toString());
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        JedisCluster jedis = RedisPool.getJedis();
        String value = jedis.get(key);
        if (value == null) {
            return null;
        }
        // 简化处理，实际应根据clazz类型做转换
        return (T) value;
    }

    @Override
    public void delete(String key) {
        JedisCluster jedis = RedisPool.getJedis();
        jedis.del(key);
    }

    @Override
    public boolean hasKey(String key) {
        JedisCluster jedis = RedisPool.getJedis();
        return jedis.exists(key);
    }

    @Override
    public String getType() {
        return cacheType;
    }
}