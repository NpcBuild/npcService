package com.npc.core.alarm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 16:18
 */
@ConfigurationProperties(prefix = "spring.alarm.mail")
@Data
@Component
public class MailConfig {
    public static final String PREFIX = "spring.alarm.mail";
    private Boolean enabled;
    private String smtpHost;
    private String smtpPort;
    private String to;
    private String from;
    private String username;
    private String password;
    private Boolean ssl;
    private Boolean debug;
}
