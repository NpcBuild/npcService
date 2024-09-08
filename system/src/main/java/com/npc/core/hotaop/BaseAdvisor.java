package com.npc.core.hotaop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author NPC
 * @description 定义该抽象Advisor目的是简化子类的具体实现（子类只需要提供具体的通知/拦截器及切入点表达式）
 * @create 2024/6/28 22:57
 */
public abstract class BaseAdvisor implements PointcutAdvisor, BeanFactoryAware {
    protected BeanFactory beanFactory;
    private String expression;
    private Advice advice;

    @Override
    public final Advice getAdvice() {
        return this.advice;
    }

    @Override
    public final Pointcut getPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(this.expression);
        pointcut.setBeanFactory(this.beanFactory);
        return pointcut;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
