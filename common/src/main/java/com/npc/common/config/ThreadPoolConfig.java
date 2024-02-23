package com.npc.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author wow
 * @createTime 2022/9/25 18:32
 * @descripttion 线程池配置
 */
@Configuration
public class ThreadPoolConfig {
    @Bean(name = "threadPool")
    public Executor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数为 3
        executor.setCorePoolSize(3);
        //最大线程数为10
        executor.setMaxPoolSize(10);
        //任务队列的大小
        executor.setQueueCapacity(3);
        //线程前缀名
        executor.setThreadNamePrefix("thread-");
        //线程存活时间
        executor.setKeepAliveSeconds(30);
        //初始化
        executor.initialize();
        return executor;
    }
}
