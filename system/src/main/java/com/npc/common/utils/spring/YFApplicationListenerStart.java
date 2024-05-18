package com.npc.common.utils.spring;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description 项目启动时运行
 * 项目初始化完毕后，才会调用方法，提供服务
 * 在CommandLineRunner和ApplicationRunner之间执行
 * @create 2024/5/18 10:34
 */
@Component
public class YFApplicationListenerStart implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        StartLog.log("YFApplicationListenerStart执行初始化操作......");
    }
}
