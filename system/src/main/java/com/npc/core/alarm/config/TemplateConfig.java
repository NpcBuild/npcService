package com.npc.core.alarm.config;

import com.npc.core.alarm.template.AlarmTemplate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 16:28
 */
@ConfigurationProperties(prefix = "spring.alarm.template")
@Data
@Component
public class TemplateConfig {

    public static final String PREFIX = "spring.alarm.template";
    private Boolean enabled;
    private String source;
    private Map<String, AlarmTemplate> templates;
}
