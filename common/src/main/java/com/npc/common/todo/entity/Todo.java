package com.npc.common.todo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Data
@TableName("t_todo")
public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    private String todoName;

    /**
     * 任务类型 1-todo 2-习惯
     */
    private String type;

    /**
     * 标签
     */
    private String tag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 任务状态 0-未开始 1-进行中 2-已完成
     */
    private String status;

    /**
     * 定时任务表主键
     */
    private Integer quartzId;

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
     * 提醒日（农历）
     */
    private String remindLunarDay;

    @TableField(exist = false)
    private String completedStatus; // 今日完成情况（查询任务列表用） 0-进行中 1-已完成

}
