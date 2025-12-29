package com.npc.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.custom")
public class RedisCustomProperties {
    /**
     * 单机模式主机地址
     */
//    private String singleHost = "127.0.0.1";
    private String singleHost = "192.168.1.20";

    /**
     * 单机模式端口
     */
    private int singlePort = 6379;

    /**
     * 集群节点列表
     */
    private List<String> clusterNodes;
}

