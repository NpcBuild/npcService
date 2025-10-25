package com.npc.common.todo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务清单表
 * </p>
 *
 * @author yangfei
 * @since 2023-09-10
 */
@Schema(name = "TodoViewVO", description = "待办任务视图对象")
@Data
public class TodoViewVO implements Serializable {

    /**
     * 主键ID
     */
    @Schema(description = "待办任务ID", example = "1")
    private Integer id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称", example = "完成项目文档")
    private String todoName;

    /**
     * 任务类型 1-todo 2-习惯
     */
    private String type;

    /**
     * 开始
     */
    private String start;

    /**
     * 结束
     */
    private String end;

    /**
     * 标签
     */
    private String tag;

    /**
     * 备注
     */
    private String remark;
    /**
     * 星标
     */
    private Boolean starMark;

    /**
     * 任务状态 0-未开始 1-进行中 2-已完成
     */
    private String status;

    /**
     * 定时任务表主键
     */
    private Integer quartzId;

    /**
     * 计划表主键
     */
    private Integer planId;

    /**
     * 问题表主键
     */
    private Integer problemId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 是否提醒
     */
    private Integer remind;
    private String remindType;

    /**
     * 1-周一，2-周二
     */
    private String remindWeek;

    /**
     * 提醒日
     */
    private String remindDay;

    /**
     * 提醒时间 时分秒
     */
    private String remindTime;

    /**
     * 提醒时动作
     */
    private String remindTodo;

    /**
     * 提醒日（农历）
     */
    private String remindLunarDay;

    /**
     * 完成后得分
     */
    private Integer doneScore;

    /**
     * 完成收获
     */
    private String doneGet;



    /**
     * 循环类型
     */
    private String recurrenceType;
    /**
     * 循环次数（每个循环周期）
     */
    private int recurrenceCount;



    /**
     * 自定义循环（如['Monday','Thursday']）
     */
    private String recurrenceDays;



    /**
     * 间隔天数（如每X天，每X周）
     */
    private Integer recurrenceInterval;



    /**
     * 下次执行时间
     */
    private LocalDateTime nextDueDate;


    @Schema(description = "完成状态", example = "0")
    private String completedStatus; // 今日完成情况（查询任务列表用） 0-进行中 1-已完成
    private LocalDateTime lastCompletedDate; // 上次任务完成时间

    private String content; // 对应计划名称

    /**
     * 循环次数（每个循环周期）
     */
    private Integer nowCount;
}
