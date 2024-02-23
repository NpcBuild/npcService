package com.npc.common.modular.dailySchedule.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author NPC
 * @description
 * @create 2024/1/31 13:17
 */
@Data
public class DailyScheduleVO {
    private Integer id;
    private LocalDate date; // 日期
    private LocalTime startTime; // 计划开始时间
    private LocalTime endTime; // 计划结束时间
    private String task; // 任务或活动名称
    private String description; // 任务或活动描述
    private String location; // 任务或活动地点
    private String status; // 任务状态（进行中、已完成等）
    private Integer priority; // 任务优先级
    private String category; // 任务类别
    private Boolean reminder; // 是否设置提醒
    private String repeatPattern; // 定期重复的模式
    private String attachments; // 附加文件、图片等
    private String participants; // 参与人员
    private Integer completionPercentage; // 任务完成百分比
    private String colorLabel; // 颜色标签
    private LocalDate deadline; // 任务截止日期
    private LocalTime notificationTime; // 任务提醒时间
    private String recurrencePattern; // 任务重复模式
    private LocalTime estimatedDuration; // 预计任务完成所需时间
    private String subtasks; // 子任务信息
    private String tags; // 标签
    private String linkedTasks; // 关联任务信息
    private String progressNotes; // 任务进展备注


    //展示用
    private String startTimeCN;
    private String endTimeCN;
}
