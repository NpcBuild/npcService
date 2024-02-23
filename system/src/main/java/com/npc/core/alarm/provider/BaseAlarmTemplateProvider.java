package com.npc.core.alarm.provider;

import com.npc.core.alarm.template.AlarmTemplate;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 15:25
 */
public abstract class BaseAlarmTemplateProvider implements AlarmTemplateProvider {

    @Override
    public AlarmTemplate loadingAlarmTemplate(String templateId) {
        if (templateId == null || templateId == "") {
//            throw new AlarmException(400, "告警模板配置id不能为空");
        }
        return getAlarmTemplate(templateId);
    }

    /**
     * 查询告警模板
     *
     * @param templateId 模板id
     * @return AlarmTemplate
     */
    abstract AlarmTemplate getAlarmTemplate(String templateId);
}
