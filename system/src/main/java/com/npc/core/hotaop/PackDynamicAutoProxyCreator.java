package com.npc.core.hotaop;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NPC
 *  实现Aop的热拔插功能
 * @description Spring容器启动过程中通过此处理器，判断当前创建的Bean实例是否相关的切入点与之匹配，如果有则对其创建对应的代理
 * @create 2024/6/28 23:17
 */
//@Component
public class PackDynamicAutoProxyCreator extends AbstractAutoProxyCreator {
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(
            Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {

        // 如果beanName是
        // sqlSessionTemplate
        // userRealm
        // securityManager
        // dispatcherServlet
        // ，则不代理
//        if ("sqlSessionTemplate".equals(beanName) || "userRealm".equals(beanName) || "securityManager".equals(beanName) || "dispatcherServlet".equals(beanName)) {
//            return DO_NOT_PROXY;
//        }

        BeanFactory beanFactory = getBeanFactory();
        if (beanFactory != null && beanFactory instanceof ConfigurableListableBeanFactory) {
            ConfigurableListableBeanFactory bf = (ConfigurableListableBeanFactory) beanFactory;
            String[] advisorNames = bf.getBeanNamesForAnnotation(PackAdvisor.class);
            List<Advisor> advisors = new ArrayList<>();
            for (String name : advisorNames) {
                advisors.add(getBeanFactory().getBean(name, Advisor.class));
            }
            return advisors.toArray();
        }
        return new Object[]{};
    }
}
