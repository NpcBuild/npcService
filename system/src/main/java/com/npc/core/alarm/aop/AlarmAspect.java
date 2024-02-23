package com.npc.core.alarm.aop;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.npc.core.alarm.AlarmFactoryExecute;
import com.npc.core.alarm.MessageTypes;
import com.npc.core.alarm.provider.AlarmTemplateProvider;
import com.npc.core.alarm.template.AlarmTemplate;
import com.npc.core.alarm.template.NotifyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author NPC
 * @description 系统警报切面
 * @create 2023/4/9 15:19
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class AlarmAspect {
    private final AlarmTemplateProvider alarmTemplateProvider;

    private final static String ERROR_TEMPLATE = "\n\n<font color=\"#F37335\">异常信息:</font>\n" +
            "```java\n" +
            "#{[exception]}\n" +
            "```\n";

    private final static String TEXT_ERROR_TEMPLATE = "\n异常信息:\n" +
            "#{[exception]}";

    private final static String MARKDOWN_TITLE_TEMPLATE = "# 【#{[title]}】\n" +
            "\n请求状态：<font color=\"#{[stateColor]}\">#{[state]}</font>\n\n";

    private final static String TEXT_TITLE_TEMPLATE = "【#{[title]}】\n" +
            "请求状态：#{[state]}\n";

    @Pointcut("@annotation(alarm)")
    public void alarmPointcut(Alarm alarm) {

    }

    @Around(value = "alarmPointcut(alarm)", argNames = "joinPoint,alarm")
    public Object around(ProceedingJoinPoint joinPoint, Alarm alarm) throws Throwable {
        Object result = joinPoint.proceed();
        if (alarm.successNotice()) {
            String templateId = alarm.templateId();
            String fileTemplateContent = "";
            if (Objects.nonNull(alarmTemplateProvider)) {
                AlarmTemplate alarmTemplate = alarmTemplateProvider.loadingAlarmTemplate(templateId);
                fileTemplateContent = alarmTemplate.getTemplateContent();
            }
            String templateContent = "";
            MessageTypes messageTye = alarm.messageType();
            if (messageTye.equals(MessageTypes.TEXT)) {
                templateContent = TEXT_TITLE_TEMPLATE.concat(fileTemplateContent);
            } else if (messageTye.equals(MessageTypes.MD)) {
                templateContent = MARKDOWN_TITLE_TEMPLATE.concat(fileTemplateContent);
            }
            Map<String, Object> alarmParamMap = new HashMap<>();
            alarmParamMap.put("title", alarm.title());
            alarmParamMap.put("stateColor", "#45B649");
            alarmParamMap.put("state", "成功");
            sendAlarm(alarm, templateContent, alarmParamMap);
        }
        return result;
    }


    @AfterThrowing(pointcut = "alarmPointcut(alarm)", argNames = "joinPoint,alarm,e", throwing = "e")
    public void doAfterThrow(JoinPoint joinPoint, Alarm alarm, Exception e) {
        log.info("请求接口发生异常 : [{}]", e.getMessage());
        String templateId = alarm.templateId();
        // 加载模板中配置的内容，若有
        String templateContent = "";
        String fileTemplateContent = "";
        if (Objects.nonNull(alarmTemplateProvider)) {
            AlarmTemplate alarmTemplate = alarmTemplateProvider.loadingAlarmTemplate(templateId);
            fileTemplateContent = alarmTemplate.getTemplateContent();
        }
        MessageTypes messageTye = alarm.messageType();
        if (messageTye.equals(MessageTypes.TEXT)) {
            templateContent = TEXT_TITLE_TEMPLATE.concat(fileTemplateContent).concat(TEXT_ERROR_TEMPLATE);
        } else if (messageTye.equals(MessageTypes.MD)) {
            templateContent = MARKDOWN_TITLE_TEMPLATE.concat(fileTemplateContent).concat(ERROR_TEMPLATE);
        }
        Map<String, Object> alarmParamMap = new HashMap<>();
        alarmParamMap.put("title", alarm.title());
        alarmParamMap.put("stateColor", "#FF4B2B");
        alarmParamMap.put("state", "失败");
        alarmParamMap.put("exception", ExceptionUtil.stacktraceToString(e));
        sendAlarm(alarm, templateContent, alarmParamMap);
    }

    private void sendAlarm(Alarm alarm, String templateContent, Map<String, Object> alarmParamMap) {
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext parserContext = new TemplateParserContext();
        String message = parser.parseExpression(templateContent, parserContext).getValue(alarmParamMap, String.class);
        MessageTypes messageTye = alarm.messageType();
        NotifyMessage notifyMessage = new NotifyMessage();
        notifyMessage.setTitle(alarm.title());
        notifyMessage.setMessageTye(messageTye);
        notifyMessage.setMessage(message);
        AlarmFactoryExecute.execute(notifyMessage);
    }
}
