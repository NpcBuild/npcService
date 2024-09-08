package com.npc.core.limit.redis;

import com.npc.core.limit.LimiterManager;
import com.npc.core.limit.entity.Limiter;
import com.npc.redis.utils.RedisUtil;
import com.npc.exception.LimitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NPC
 * @description
 * 1.buildLuaScript()
 * 2.buildLuaScript2()
 * 3.@PostConstruct注入 ， 直接使用 redisScript（避免每次执行都会构建lua执行脚本）
 * @create 2023/4/13 21:43
 */
@Slf4j
public class RedisLimiter implements LimiterManager {
    @Resource
    private RedisUtil redisUtil;

    private DefaultRedisScript<Long> redisScript;
    public RedisLimiter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @PostConstruct
    public void init(){
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimiter.lua")));
    }

    @Override
    public boolean tryAccess(Limiter limiter) {

        String key = limiter.getKey();
        List<String> keys = new ArrayList<>();
        keys.add( key );

        int seconds = limiter.getSeconds();
        int limitCount = limiter.getLimitNum();

        String luaScript = buildLuaScript();
//        String luaScript = buildLuaScript2();

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

    /**
     * 构建redis lua脚本
     * @return
     */
    private String buildLuaScript2() {
        StringBuilder luaString = new StringBuilder();
        luaString.append( "local key = KEYS[1]" );
        //获取ARGV内参数Limit
        luaString.append( "\nlocal limit = tonumber(ARGV[1])" );
        //获取key的次数
        luaString.append( "\nlocal curentLimit = tonumber(redis.call('get', key) or \"0\")" );
        luaString.append( "\nif curentLimit + 1 > limit then" );
        luaString.append( "\nreturn 0" );
        luaString.append( "\nelse" );
        //自增长 1
        luaString.append( "\n redis.call(\"INCRBY\", key, 1)" );
        //设置过期时间
        luaString.append( "\nredis.call(\"EXPIRE\", key, ARGV[2])" );
        luaString.append( "\nreturn curentLimit + 1" );
        luaString.append( "\nend" );
        return luaString.toString();
    }
}
