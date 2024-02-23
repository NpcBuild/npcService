package com.npc.common.modular.dailySchedule.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

/**
 * <p>
 * 每日日程安排
 * </p>
 *
 * @author yangfei
 * @since 2023-12-18
 */
@Data
@TableName("t_daily_schedule")
public class DailySchedule implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 唯一标识符，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;



    /**
     * 日期
     */
    private LocalDate date;



    /**
     * 计划开始时间
     */
    private LocalTime startTime;



    /**
     * 计划结束时间
     */
    private LocalTime endTime;



    /**
     * 任务或活动名称
     */
    private String task;



    /**
     * 任务或活动描述
     */
    private String description;



    /**
     * 任务或活动地点
     */
    private String location;



    /**
     * 任务状态（进行中、已完成等）
     */
    private String status;



    /**
     * 任务优先级
     */
    private Integer priority;



    /**
     * 任务类别
     */
    private String category;



    /**
     * 是否设置提醒
     */
    private Boolean reminder;



    /**
     * 定期重复的模式
     */
    private String repeatPattern;



    /**
     * 附加文件、图片等
     */
    private String attachments;



    /**
     * 参与人员
     */
    private String participants;



    /**
     * 任务完成百分比
     */
    private Integer completionPercentage;



    /**
     * 颜色标签
     */
    private String colorLabel;



    /**
     * 任务截止日期
     */
    private LocalDate deadline;



    /**
     * 任务提醒时间
     */
    private LocalTime notificationTime;



    /**
     * 任务重复模式
     */
    private String recurrencePattern;



    /**
     * 预计任务完成所需时间
     */
    private LocalTime estimatedDuration;



    /**
     * 子任务信息
     */
    private String subtasks;



    /**
     * 标签
     */
    private String tags;



    /**
     * 关联任务信息
     */
    private String linkedTasks;



    /**
     * 任务进展备注
     */
    private String progressNotes;

}
