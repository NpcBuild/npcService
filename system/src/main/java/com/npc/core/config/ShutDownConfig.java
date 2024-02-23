package com.npc.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NPC
 * @description 提供一个获取bean的方法，这样TerminateBean对象会被初始化
 * @create 2023/8/29 20:27
 */
@Configuration
public class ShutDownConfig {

    @Bean
    public TerminateBean getTerminateBean() {
        System.out.println("加载了TerminateBean！");
        return new TerminateBean();
    }
}
