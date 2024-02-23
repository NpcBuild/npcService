package com.npc.core.alarm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 16:27
 */
@ConfigurationProperties(prefix = "spring.alarm.dingtalk")
@Data
@Component
@Primary
public class DingTalkConfig {

    public static final String PREFIX = "spring.alarm.dingtalk";
    private Boolean enabled;
    private String token;
    private String secret;
}
