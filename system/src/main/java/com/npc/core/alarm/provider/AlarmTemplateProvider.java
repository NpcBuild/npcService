package com.npc.core.alarm.provider;

import com.npc.core.alarm.template.AlarmTemplate;

public interface AlarmTemplateProvider {
    /**
     * 加载告警模板
     *
     * @param templateId 模板id
     * @return AlarmTemplate
     */
    AlarmTemplate loadingAlarmTemplate(String templateId);
}
