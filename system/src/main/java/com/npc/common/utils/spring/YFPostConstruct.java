package com.npc.common.utils.spring;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author NPC
 * @description 项目启动时运行
 * 当Spring创建了一个类的实例并完成依赖注入后，如果该类中存在被@PostConstruct注解的方法，Spring会自动调用这个方法
 * 注意：这个注解只能应用于非静态方法，但是可以是final的，并且只会被容器调用一次
 * 这个方法的访问修饰符可以是public、protected或private，因为它的功能是通过反射来实现的
 * 这个方法可以有返回值
 * @create 2024/5/18 10:16
 */
@Component
public class YFPostConstruct {
    @PostConstruct
    public void init() {
        StartLog.log("YFPostConstruct执行初始化操作......");
    }
}
