package com.npc.core.hotaop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author NPC
 * @description
 * @create 2024/6/28 23:03
 */
@PackAdvisor
public class TimeTookAdvisor extends BaseAdvisor {
    public TimeTookAdvisor() {
        setAdvice(new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                long start = System.currentTimeMillis();
                Object ret = invocation.proceed();
                System.err.printf("业务执行耗时：%dms%n", (System.currentTimeMillis() - start));
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
