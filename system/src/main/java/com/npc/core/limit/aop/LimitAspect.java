package com.npc.core.limit.aop;

import com.npc.core.limit.LimiterManager;
import com.npc.core.limit.entity.Limiter;
import com.npc.exception.LimitException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;

/**
 * @author NPC
 * @description
 * @create 2023/4/15 20:18
 */
@Slf4j
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true) //使用CGLIB代理
@Conditional(LimitAspectCondition.class) // 使用了自定义条件选择器，只有当配置类中出现了limit.type属性时才会加载这个AOP
public class LimitAspect {

    @Setter(onMethod_ = @Autowired)
    private LimiterManager limiterManager;

    @Pointcut("@annotation(com.npc.core.limit.aop.Limit)")
    private void check() {

    }

    @Before("check()")
    public void before(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //Limit，如果存在则说明需要限流
        Limit limit = method.getAnnotation(Limit.class);
        if(limit != null){

            Limiter limiter = Limiter.builder().limitNum((int) limit.permitsPerSecond())
                    .seconds(limit.timeout())
                    .key(limit.key()).build();

            String key = limiter.getKey();
            if (StringUtils.isEmpty(key)) {
                throw new LimitException( "redis limiter key cannot be null" );
            }
            if(!limiterManager.tryAccess(limiter)){
                log.debug("令牌桶={}，获取令牌失败",key);
                throw new LimitException( "There are currently many people , please try again later!" );
            }
        }
    }
}
