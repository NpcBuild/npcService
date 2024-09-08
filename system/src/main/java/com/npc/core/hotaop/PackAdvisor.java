package com.npc.core.hotaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解的作用就是获取当前容器中定义的所有Advisor
 * 只有使用了该注解的类可以被添加到切面集合中
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PackAdvisor {
}
