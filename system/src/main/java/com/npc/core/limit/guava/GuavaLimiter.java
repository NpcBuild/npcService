package com.npc.core.limit.guava;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.npc.core.limit.LimiterManager;
import com.npc.core.limit.entity.Limiter;
import lombok.extern.slf4j.Slf4j;
//import org.apache.lucene.store.RateLimiter;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author NPC
 * @description
 * @create 2023/4/13 21:38
 */
@Slf4j
public class GuavaLimiter implements LimiterManager {
    private final Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Override
    public boolean tryAccess(Limiter limiter) {
        RateLimiter rateLimiter = getRateLimiter(limiter);
        if (rateLimiter == null) {
            return false;
        }
        // 拿令牌
        boolean access = rateLimiter.tryAcquire(1,100, TimeUnit.MILLISECONDS);

        log.info("{} access :{}",limiter.getKey(),access);

        return access;
    }

    private RateLimiter getRateLimiter(Limiter limiter) {
        String key = limiter.getKey();
        RateLimiter rateLimiter = null;
//        验证缓存是否有命中key
        if (!limiterMap.containsKey(key)) {
            // 创建令牌桶
            rateLimiter = RateLimiter.create(limiter.getLimitNum());
            limiterMap.put(key, rateLimiter);
            log.info("新建了令牌桶={}，容量={}",key,limiter.getLimitNum());
        }
        rateLimiter = limiterMap.get(key);
        return rateLimiter;
    }
}