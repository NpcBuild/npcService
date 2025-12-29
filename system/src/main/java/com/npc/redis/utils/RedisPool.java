package com.npc.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Slf4j
public class RedisPool {

    private static JedisCluster pool;
    private static boolean enabled = true;

    private static Integer maxTotal = 300;
    private static Integer maxIdle = 100;
    private static Integer maxWait = 10000;
    private static Boolean testOnBorrow = true;

    private static void initPool() {
        // 检查是否启用了 Redis
        String redisMode = System.getProperty("app.redis.mode", "none");
        if ("none".equals(redisMode)) {
            log.info("Redis is disabled, skipping initialization.");
            enabled = false;
            return;
        }

        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMaxIdle(maxIdle);
            config.setTestOnBorrow(testOnBorrow);
            config.setBlockWhenExhausted(true);
            config.setMaxWaitMillis(maxWait);

            Set<HostAndPort> shareInfos = new LinkedHashSet<>();

            if ("cluster".equals(redisMode)) {
                // 集群模式
                String clusterNodesStr = System.getProperty("app.redis.cluster.nodes");
                if (clusterNodesStr != null && !clusterNodesStr.isEmpty()) {
                    String[] clusterNodes = clusterNodesStr.split(",");
                    for (String node : clusterNodes) {
                        String[] parts = node.trim().split(":");
                        if (parts.length == 2) {
                            shareInfos.add(new HostAndPort(parts[0], Integer.parseInt(parts[1])));
                        }
                    }
                }
            } else if ("single".equals(redisMode)) {
                // 单机模式（备用）
//                String redisHost = System.getProperty("app.redis.host", "127.0.0.1");
                String redisHost = System.getProperty("app.redis.host", "192.168.1.20");
                int redisPort = Integer.parseInt(System.getProperty("app.redis.port", "6379"));
                // 注意：单机模式应该使用 Jedis 而不是 JedisCluster
                // 这里只是示例，实际应该根据模式选择合适的客户端
                shareInfos.add(new HostAndPort(redisHost, redisPort));
            }

            pool = new JedisCluster(shareInfos, config);
            log.info("JedisCluster initialized successfully with {} nodes", shareInfos.size());
        } catch (Exception e) {
            log.error("Failed to initialize JedisCluster", e);
            enabled = false;
        }
    }

    static {
        initPool();
    }

    public static JedisCluster getJedis() {
        if (!enabled) {
            throw new RuntimeException("Redis is not enabled or failed to initialize");
        }
        return pool;
    }

    public static void jedisPoolClose(JedisCluster jedis) {
        if (jedis != null && enabled) {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error("Failed to close jedis cluster connection", e);
            }
        }
    }
}
