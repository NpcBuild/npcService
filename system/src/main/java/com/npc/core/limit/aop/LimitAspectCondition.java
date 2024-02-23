package com.npc.core.limit.aop;

import com.npc.Constants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


/**
 * @author NPC
 * @description
 * @create 2023/4/15 20:22
 */
public class LimitAspectCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //检查配置文件是否包含limit.type属性
        return conditionContext.getEnvironment().containsProperty(Constants.LIMIT_TYPE);
    }
}
