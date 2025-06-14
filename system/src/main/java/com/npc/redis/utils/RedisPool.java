package com.npc.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

//        System.out.println("当前生效的环境配置是: " + activeProfile);
        //        dev环境
        shareInfos.add(new HostAndPort("localhost", 6380));
        shareInfos.add(new HostAndPort("localhost", 6381));
        shareInfos.add(new HostAndPort("localhost", 6382));
        shareInfos.add(new HostAndPort("localhost", 6383));
        shareInfos.add(new HostAndPort("localhost", 6384));
        shareInfos.add(new HostAndPort("localhost", 6385));
        //        docker环境
        shareInfos.add(new HostAndPort("192.168.1.20", 6380));
        shareInfos.add(new HostAndPort("192.168.1.20", 6381));
        shareInfos.add(new HostAndPort("192.168.1.20", 6382));
        shareInfos.add(new HostAndPort("192.168.1.20", 6383));
        shareInfos.add(new HostAndPort("192.168.1.20", 6384));
        shareInfos.add(new HostAndPort("192.168.1.20", 6385));
        //        pro环境
        shareInfos.add(new HostAndPort("172.16.0.11", 6379));
        shareInfos.add(new HostAndPort("172.16.0.12", 6379));
        shareInfos.add(new HostAndPort("172.16.0.13", 6379));
        shareInfos.add(new HostAndPort("172.16.0.14", 6379));
        shareInfos.add(new HostAndPort("172.16.0.15", 6379));
        shareInfos.add(new HostAndPort("172.16.0.16", 6379));

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
