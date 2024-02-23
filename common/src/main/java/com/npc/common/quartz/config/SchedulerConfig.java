package com.npc.common.quartz.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author wow
 * @createTime 2022/9/17 23:32
 * @descripttion Quartz配置
 */

//todo 未实现
@Configuration
public class SchedulerConfig {
//    /**
//     * 读取配置文件，将值初始化
//     */
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }
//    /**
//     * 将配置文件的数据加载到SchedulerFactoryBean中
//     */
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setQuartzProperties(quartzProperties());
//        return schedulerFactoryBean;
//    }
//    /**
//     * 初始化监听器
//     */
//    @Bean
//    public QuartzInitializerListener executorListener() {
//        return new QuartzInitializerListener();
//    }
//    /**
//     * 获得Scheduler对象
//     */
//    @Bean
//    public Scheduler scheduler() throws IOException{
//        return schedulerFactoryBean().getScheduler();
//    }
}
