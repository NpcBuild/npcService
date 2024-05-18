package com.npc.common.utils.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description 项目启动时运行
 * InitializingBean允许Bean在其属性被设置后进行自定义初始化。
 * afterPropertiesSet在Bean的所有属性被设置后立即调用。可以作为自定义初始化回调的标记接口，用于执行那些需要在对象依赖注入完成后进行的初始化操作
 * 这在需要在Bean初始化阶段执行一些特定操作时非常有用，例如数据验证、资源初始化或与其他Bean的交互等
 * 传统但不如使用@PostConstruct注解优雅，且增加了类的耦合度
 * @create 2024/5/18 10:27
 */
@Component
public class YFInitializingBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        StartLog.log("YFInitializingBean执行初始化操作......");
    }
}
