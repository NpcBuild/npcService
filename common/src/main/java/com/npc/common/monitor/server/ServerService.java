package com.npc.common.monitor.server;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author NPC
 * @description
 * @create 2023/11/30 21:20
 */
@Service
public class ServerService {
    public static boolean IS_LINUX; // 是否是linux系统
    /**
     * 判断是否是linux系统
     */
    @Bean
    public static boolean isLinux() {
        return IS_LINUX;
    }

    @PostConstruct
    public void init() {
        String osName = System.getProperty("os.name");
        System.out.println("OS Name: " + osName);
        IS_LINUX = osName.toLowerCase().contains("linux");
    }
}
