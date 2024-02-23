package com.npc.common.monitor.server;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author NPC
 * @description
 * @create 2023/11/30 21:20
 */
@Service
public class ServerService {
    public static boolean IS_LINUX = false; // 是否是linux系统
    /**
     * 判断是否是linux系统
     */
    @Bean
    public static boolean isLinux() {
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        boolean isLinux = osName.toLowerCase().contains("linux");
        if (isLinux) IS_LINUX = true;
        return isLinux;
    }
}
