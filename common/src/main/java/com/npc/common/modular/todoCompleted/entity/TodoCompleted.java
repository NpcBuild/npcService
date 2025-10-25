package com.npc.common.modular.todoCompleted.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务完成情况表
 * </p>
 *
 * @author yangfei
 * @since 2023-12-01
 */
@Schema(name = "TodoCompleted", description = "已完成待办任务实体类")
@Data
@TableName("t_todo_completed")
public class TodoCompleted implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
    @Schema(description = "ID", example = "1")
    private Integer id;



    /**
     * 任务ID
     */
    @Schema(description = "待办任务ID", example = "1")
    private Integer todoId;



    /**
     * 任务状态 0-未开始 1-进行中 2-已完成
     */
    @Schema(description = "完成状态", example = "2")
    private String status;
    /**
     * 完成次数（针对计数的任务）
     */
    @Schema(description = "完成次数", example = "-")
    private Integer completeNum;



    /**
     * 完成时间
     */
    @Schema(description = "完成时间", example = "2024-01-01T12:00:00")
    private LocalDateTime finishTime;



    /**
     * 得分
     */
    @Schema(description = "积分", example = "10")
    private Integer score;

}
