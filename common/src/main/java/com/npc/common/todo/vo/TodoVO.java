package com.npc.common.todo.vo;

import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author NPC
 * @description
 * @create 2023/9/10 8:36
 */
@Schema(name = "TodoVO", description = "待办任务查询对象")
@Data
public class TodoVO extends QuartzJobVO {
    /**
     * 主键ID
     */
    @Schema(description = "待办任务ID", example = "1")
    private Integer id;
    @Schema(description = "ID列表，用逗号分隔", example = "1,2,3")
    private String ids;

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
    private Integer problemId; //问题表主键
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

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime endTime;

    private Boolean done; // 任务是否完成
    @Schema(description = "日期", example = "2024-01-01")
    private String date; // 查询用
    @Schema(description = "开始日期", example = "2024-01-01")
    private String startDate; // 查询用
    @Schema(description = "结束日期", example = "2024-01-31")
    private String endDate; // 查询用
    private String resType; // 查询结果结构

}
