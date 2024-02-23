package com.npc.core.alarm.config;

import com.npc.core.alarm.service.WorkWeXinWarnService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 17:53
 */
@Configuration
public class ScanService {
    @Bean
    public WorkWeXinWarnService workWeXinWarnService() {
        return new WorkWeXinWarnService();
    }
}
