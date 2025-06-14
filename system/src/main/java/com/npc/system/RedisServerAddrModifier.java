package com.npc.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
            // 获取系统环境变量 是dev还是pro
            String env = System.getenv("ENV");
            if (env == null) {
                env = "dev"; // 默认开发环境
            }
            System.out.println("当前系统环境是：" + env);
            // 获取代码运行环境中的Redis是单机模式还是集群模式
            String redisMode = System.getenv("REDIS_MODE");
            if (redisMode == null) {
                redisMode = "single"; // 默认单机模式
            }

//            // 根据上述结果，修改生效的Redis环境配置
//            Map<String, Object> source = new HashMap<>();
//            if ("dev".equals(env)) {
//                if ("single".equals(redisMode)) {
//                    // 开发环境单机模式
//                    source.put("spring.redis.host", "127.0.0.1");
//                    source.put("spring.redis.port", 6379);
//                } else {
//                    // 开发环境集群模式
//                    source.put("spring.redis.cluster.nodes", "127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381");
//                }
//            } else {
//                if ("single".equals(redisMode)) {
//                    // 生产环境单机模式
//                    source.put("spring.redis.host", "prod-redis-host");
//                    source.put("spring.redis.port", 6379);
//                } else {
//                    // 生产环境集群模式
//                    source.put("spring.redis.cluster.nodes", "prod-node1:6379,prod-node2:6379,prod-node3:6379");
//                }
//            }
//
//            MapPropertySource propertySource = new MapPropertySource("dynamicRedisConfig", source);
//            // 将自定义属性源添加到属性源列表的最前面
//            environment.getPropertySources().addFirst(propertySource);
//            System.out.println("根据环境和模式，实时修改Redis配置为：" + source);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("修改 Redis 配置时出现异常: " + e.getMessage());
        }
    }
}
