package com.npc.common.modular.events.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: npcService
 * @description 月信息
 * @author: feiyang
 * @create: 2026/01/02 20:32
 **/
@Data
public class EventsMonthInfoVO {
    // 月份 yyyy-MM
    private String month;
    // 主题
    private String theme;
    // 目标
    private String goal;
}
