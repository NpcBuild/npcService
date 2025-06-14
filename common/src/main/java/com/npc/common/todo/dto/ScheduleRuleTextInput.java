package com.npc.common.todo.dto;

import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2025/5/18 19:47
 */
@Data
public class ScheduleRuleTextInput {
    private String ruleText;
    private String taskId;
    private String startDate;
    private String endDate;
}
