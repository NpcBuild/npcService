package com.npc.core.alarm.provider;

import com.npc.core.alarm.config.TemplateConfig;
import com.npc.core.alarm.template.AlarmTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 15:25
 */
@RequiredArgsConstructor
public class YamlAlarmTemplateProvider extends BaseAlarmTemplateProvider {

    private final TemplateConfig templateConfig;

    @Override
    AlarmTemplate getAlarmTemplate(String templateId) {
        Map<String, AlarmTemplate> configTemplates = templateConfig.getTemplates();
        AlarmTemplate alarmTemplate = configTemplates.get(templateId);
        if (ObjectUtils.isEmpty(alarmTemplate)) {
//            throw new AlarmException(400, "未发现告警配置模板");
        }
        return alarmTemplate;
    }
}
