package com.npc.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
//@ConditionalOnBean(StringRedisTemplate.class) // 只有当 StringRedisTemplate 存在时才创建这个 bean
public class RedisUtil {

    @Autowired(required = false) // 设置为非必需
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        if (stringRedisTemplate == null) {
            log.warn("Redis is not available, set operation ignored");
            return;
        }
        stringRedisTemplate.opsForValue().set(key, value);
    }
    public void set(String key, String value, long timeout) {
        if (stringRedisTemplate == null) {
            log.warn("Redis is not available, set operation ignored");
            return;
        }
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
    public String get(String key) {
        if (stringRedisTemplate == null) {
            return null;
        }
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        if (stringRedisTemplate == null) {
            return false;
        }
        return stringRedisTemplate.hasKey(key);
    }


    /**
     * 增加zset
     * @param key
     * @param value
     * @param score
     */
    public void addToZset(String key, String value, double score) {
        if (stringRedisTemplate == null) {
            log.warn("Redis is not available, addToZset operation ignored");
            return;
        }
        stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 从zset中删除
     * @param key
     * @param value
     */
    public void removeFromZset(String key, String value) {
        if (stringRedisTemplate == null) {
            log.warn("Redis is not available, removeFromZset operation ignored");
            return;
        }
        stringRedisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 获取zset的排名
     * @param key
     * @param value
     * @return
     */
    public Long getZset(String key, String value) {
        if (stringRedisTemplate == null) {
            return null;
        }
        return stringRedisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取zset的分数
     * @param key
     * @param value
     * @return
     */
    public Double getScore(String key, String value) {
        if (stringRedisTemplate == null) {
            return null;
        }
        return stringRedisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 获取zset的前n个元素
     * @param key
     * @param count
     * @return
     */
    public Iterable<String> getTopZset(String key, int count) {
        if (stringRedisTemplate == null) {
            return null;
        }
        return stringRedisTemplate.opsForZSet().reverseRange(key, 0, count - 1);
    }

    // 执行Redis命令，并返回执行结果
    public Long execute(RedisScript<Long> script, List<String> keys, String... args) {
        if (stringRedisTemplate == null) {
            log.warn("Redis is not available, execute operation ignored");
            return 0L;
        }

        // 优先使用 Spring Data Redis 的方式执行脚本
        try {
            return stringRedisTemplate.execute(script, keys, args);
        } catch (Exception e) {
            log.error("Failed to execute Redis script via Spring Data Redis", e);

            // 回退到直接使用 JedisCluster
            JedisCluster jedis = null;
            try {
                jedis = RedisPool.getJedis();
                // 注意：这里需要根据实际情况调整实现
                // 暂时返回默认值
                return 0L;
            } catch (Exception ex) {
                log.error("updateStock 获取 Jedis 实例失败：", ex);
            } finally {
                RedisPool.jedisPoolClose(jedis);
            }
        }
        return 0L; // Default value if something goes wrong
    }
}
