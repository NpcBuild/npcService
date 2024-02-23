package com.npc.core.limit.redis;

import com.npc.core.limit.LimiterManager;
import com.npc.core.limit.entity.Limiter;
import com.npc.redis.utils.RedisUtil;
import com.npc.exception.LimitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2023/4/13 21:43
 */
@Slf4j
public class RedisLimiter implements LimiterManager {
    @Resource
    private RedisUtil redisUtil;

    public RedisLimiter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean tryAccess(Limiter limiter) {

        String key = limiter.getKey();
        List<String> keys = new ArrayList<>();
        keys.add( key );

        int seconds = limiter.getSeconds();
        int limitCount = limiter.getLimitNum();

        String luaScript = buildLuaScript();

        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);

        Long count = redisUtil.execute( redisScript, keys, "" + limitCount, "" + seconds );

        log.info( "Access try count is {} for key={}", count, key );

        return count != null && count != 0;
    }

    // 构建限流器的Lua脚本
    private String buildLuaScript() {
        return "local count = redis.call('incr', KEYS[1])\n" +
                "if tonumber(count) == 1 then\n" +
                "   redis.call('expire', KEYS[1], tonumber(ARGV[1]))\n" +
                "end\n" +
                "if tonumber(count) > tonumber(ARGV[2]) then\n" +
                "   return 0\n" +
                "end\n" +
                "return 1\n";
    }
}
