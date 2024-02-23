package com.npc.core.alarm.aop;

import com.npc.core.alarm.MessageTypes;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Alarm {

    /**
     * 报警标题
     *
     * @return String
     */
    String title() default "";

    /**
     * 发送报警格式：目前支持text，markdown
     * @return
     */
    MessageTypes messageType() default MessageTypes.TEXT;

    /**
     * 告警模板id
     * @return
     */
    String templateId() default "";

    /**
     * 成功是否通知：true-通知，false-不通知
     * @return
     */
    boolean successNotice() default false;
}
