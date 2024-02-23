package com.npc.core.alarm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 16:25
 */
@ConfigurationProperties(prefix = "spring.alarm.wechat")
@Data
@Component
public class WorkWeXinConfig {

    public static final String PREFIX = "spring.alarm.wechat";
    private Boolean enabled;
    private String key;
    private String toUser;
}
