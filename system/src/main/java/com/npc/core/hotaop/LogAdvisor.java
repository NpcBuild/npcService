package com.npc.core.hotaop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author NPC
 * @description
 * @create 2024/6/28 23:12
 */
@PackAdvisor
public class LogAdvisor extends BaseAdvisor{
    public LogAdvisor() {
        setAdvice(new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                System.out.println("记录日志...");
                Object ret = invocation.proceed();
                return ret;
            }
        });
//        setExpression("execution(* com.npc..*.*(..))");
        setExpression("execution(* com.npc.common..*.*(..))");
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }
}
