package com.npc.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Slf4j
public class RedisPool {

    private static JedisCluster  pool;

    private static Integer maxTotal = 300;

    private static Integer maxIdle = 100;

    private static Integer maxWait = 10000;

    private static Boolean testOnBorrow = true;

    @Autowired
    private Environment environment;

////    @Value("${spring.redis.host}")
//    private static String redisIP = "localhost";
////    @Value("${spring.redis.port}")
//    private static Integer redisPort = 6380;

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setBlockWhenExhausted(true);
        config.setMaxWaitMillis(maxWait);

        Set<HostAndPort> shareInfos = new LinkedHashSet<HostAndPort>();
        //        dev环境
        shareInfos.add(new HostAndPort("localhost", 6380));
        shareInfos.add(new HostAndPort("localhost", 6381));
        shareInfos.add(new HostAndPort("localhost", 6382));
        shareInfos.add(new HostAndPort("localhost", 6383));
        shareInfos.add(new HostAndPort("localhost", 6384));
        shareInfos.add(new HostAndPort("localhost", 6385));
        //        pro环境
        shareInfos.add(new HostAndPort("172.12.0.11", 6379));
        shareInfos.add(new HostAndPort("172.12.0.12", 6379));
        shareInfos.add(new HostAndPort("172.12.0.13", 6379));
        shareInfos.add(new HostAndPort("172.12.0.14", 6379));
        shareInfos.add(new HostAndPort("172.12.0.15", 6379));
        shareInfos.add(new HostAndPort("172.12.0.16", 6379));

        pool = new JedisCluster(shareInfos, config);
    }

    // 类加载到 jvm 时直接初始化连接池
    static {
        initPool();
    }

    public static JedisCluster getJedis() {
        return pool;
    }

    //todo 实现上方的获取实例
    public static void jedisPoolClose(JedisCluster jedis) {
        if (jedis != null) {
//            try {
//                jedis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
