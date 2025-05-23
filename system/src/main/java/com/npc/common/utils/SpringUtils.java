package com.npc.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description spring工具类
 * @create 2023/6/20 20:36
 */
@Component
//@Lazy(false)
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    /**
     * 重写setApplicationContext方法，能从该方法中获取到spring容器对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("****** ApplicationContext配置成功 ******");
        System.out.println("****** 在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象 ******");
        System.out.println("****** applicationContext=" + SpringUtils.applicationContext + " ******");
        System.out.println("---------------------------------------------------------------------");
    }

    //获取applicationContext
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 获取当前SpringBoot运行的进程号
     * @return
     */
    public static String getId() {
        ApplicationPid pid = new ApplicationPid();
        System.out.printf("进程ID：%s%n", pid.toString());
        return pid.toString();
    }
}
