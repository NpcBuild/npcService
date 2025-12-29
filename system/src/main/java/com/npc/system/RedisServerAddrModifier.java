package com.npc.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * @author NPC
 * @description 在项目启动时，根据系统环境和代码运行环境，自动修改连接的redis服务地址
 * EnvironmentPostProcessor 接口允许你在 Spring 应用上下文创建之前对环境配置进行修改
 * @create 2025/2/5 12:00
 */
public class RedisServerAddrModifier implements EnvironmentPostProcessor {

    // 检查指定 IP 和端口的服务是否可用
    private boolean isServiceAvailable(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 2000); // 超时时间设置为 2 秒
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            // 获取配置的集群节点
            String clusterNodesStr = environment.getProperty("spring.redis.cluster.nodes");
            String redisHost = environment.getProperty("spring.redis.host", "192.168.1.20");
            String redisPortStr = environment.getProperty("spring.redis.port", "6379");
            int redisPort = Integer.parseInt(redisPortStr);

            // 检测 Redis 环境
            String redisType = detectRedisEnvironment(redisHost, redisPort, clusterNodesStr);

            Map<String, Object> source = new HashMap<>();

            switch (redisType) {
                case "cluster":
                    // 配置集群模式
                    source.put("spring.redis.enabled", true);
                    source.put("spring.redis.cluster.enabled", true);
                    source.put("spring.redis.cluster.nodes", clusterNodesStr);
                    source.put("spring.redis.timeout", "2000ms");
                    source.put("app.cache.type", "redis-cluster");
                    System.setProperty("app.redis.mode", "cluster");
                    break;
                case "single":
                    // 配置单机模式
                    source.put("spring.redis.enabled", true);
                    source.put("spring.redis.cluster.enabled", false);
                    source.put("spring.redis.host", redisHost);
                    source.put("spring.redis.port", redisPort);
                    source.put("spring.redis.timeout", "2000ms");
                    source.put("spring.redis.lettuce.pool.max-active", 8);
                    source.put("spring.redis.lettuce.pool.max-idle", 8);
                    source.put("spring.redis.lettuce.pool.min-idle", 0);
                    source.put("app.cache.type", "redis-single");
                    System.setProperty("app.redis.mode", "single");
                    break;
                case "none":
                default:
                    // 不启用 Redis，使用 Guava Cache
                    source.put("spring.redis.enabled", false);
                    source.put("spring.redis.cluster.enabled", false);
                    source.put("app.cache.type", "guava");
                    System.setProperty("app.redis.mode", "none");
                    break;
            }

            MapPropertySource propertySource = new MapPropertySource("dynamicRedisConfig", source);
            environment.getPropertySources().addFirst(propertySource);
            System.out.println("根据环境检测，实时修改Redis配置为：" + source);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("修改 Redis 配置时出现异常: " + e.getMessage());
        }
    }

    /**
     * 检测 Redis 环境类型
     * @return "cluster" | "single" | "none"
     */
    private String detectRedisEnvironment(String singleHost, int singlePort, String clusterNodesStr) {
        // 如果配置了集群节点，则检查集群节点是否可用
        if (clusterNodesStr != null && !clusterNodesStr.isEmpty()) {
            String[] clusterNodes = clusterNodesStr.split(",");
            int availableClusterNodes = 0;
            for (String node : clusterNodes) {
                String[] parts = node.trim().split(":");
                if (parts.length == 2 && isServiceAvailable(parts[0], Integer.parseInt(parts[1]))) {
                    availableClusterNodes++;
                }
            }

            // 如果至少有3个集群节点可用，则认为是集群环境
            if (availableClusterNodes >= 3) {
                return "cluster";
            }
        }

        // 检查单机节点是否可用
        if (isServiceAvailable(singleHost, singlePort)) {
            return "single";
        }

        // 没有可用的 Redis 服务
        return "none";
    }
}
