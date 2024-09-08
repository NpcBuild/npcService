package com.npc.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author NPC
 * @description redis操作工具类
 * @create 2023/4/10 21:33
 */
@Slf4j
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    // 执行Redis命令，并返回执行结果
    public Long execute(RedisScript<Long> script, List<String> keys, String... args) {
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
//            Object result = jedis.eval(script.getScriptAsString().getBytes(), ReturnType.INTEGER, keys.size(), keys.toArray(new String[0]), args);
//            Object result = jedis.eval(script.getScriptAsString(), ReturnType.INTEGER, keys.size(), keys.toArray(new String[0]), args);
//            if (result instanceof Long) {
//                return (Long) result;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateStock 获取 Jedis 实例失败：", e);
        } finally {
            RedisPool.jedisPoolClose(jedis);
        }
        return 0L; // Default value if something goes wrong
    }
}
