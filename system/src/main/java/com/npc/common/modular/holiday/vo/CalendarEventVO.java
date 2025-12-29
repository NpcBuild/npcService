package com.npc.common.modular.holiday.vo;

import lombok.Data;

/**
 * @program: npcService
 * @description 日历事件展示VO
 * @author: feiyang
 * @create: 2025/11/10 09:38
 **/
@Data
public class CalendarEventVO {
    private String id;
    private String title;
    private String date;
    private String dateType = "gregorian";
    private String eventType;
    private String description;
    private String color;
    private boolean important = false;
    // 是否为用户自定义事件
    private boolean custom = false;
}
