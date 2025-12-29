package com.npc.client.Minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: npcService
 * @description 配置类
 * @author: feiyang
 * @create: 2025/11/29 16:55
 **/

@Component
@Data
@ConfigurationProperties(prefix = "vehicle.minio")
public class MinioProperties {
    private String url;
    private String username;
    private String password;
    private String bucketName;
}
