package com.npc.core.alarm.provider;

import com.npc.core.alarm.template.AlarmTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 15:28
 */
@RequiredArgsConstructor
public class JdbcAlarmTemplateProvider extends BaseAlarmTemplateProvider {

    private final Function<String, AlarmTemplate> function;

    @Override
    AlarmTemplate getAlarmTemplate(String templateId) {
        AlarmTemplate alarmTemplate = function.apply(templateId);
        if (ObjectUtils.isEmpty(alarmTemplate)) {
//            throw new AlarmException(400, "未发现告警配置模板");
        }
        return alarmTemplate;
    }
}
