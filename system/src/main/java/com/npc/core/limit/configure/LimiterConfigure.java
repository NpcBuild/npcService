package com.npc.core.limit.configure;

import com.npc.core.limit.LimiterManager;
import com.npc.core.limit.guava.GuavaLimiter;
import com.npc.core.limit.redis.RedisLimiter;
import com.npc.redis.utils.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NPC
 * @description 根据配置文件注入限流实现类，当配置文件中属性 limit.type=local 时启用Guava限流机制，当limit.type=redis 时启用Redis限流机制
 * @create 2023/4/15 20:15
 */
@Configuration
public class LimiterConfigure {

    @Bean
    @ConditionalOnProperty(name = "limit.type",havingValue = "local")
    public LimiterManager guavaLimiter(){
        return new GuavaLimiter();
    }


    @Bean
    @ConditionalOnProperty(name = "limit.type",havingValue = "redis")
    public LimiterManager redisLimiter(RedisUtil redisUtil){
        return new RedisLimiter(redisUtil);
    }
}
