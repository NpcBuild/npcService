package com.npc.core.config;

import javax.annotation.PreDestroy;

/**
 * @author NPC
 * @description 终止Bean
 * @create 2023/8/29 20:24
 */
public class TerminateBean {
    @PreDestroy
    public void preDestory() {
        System.out.println("程序终止了！");
    }
}
