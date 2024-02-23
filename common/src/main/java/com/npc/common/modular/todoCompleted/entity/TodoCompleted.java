package com.npc.common.modular.todoCompleted.entity;

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
@Data
@TableName("t_todo_completed")
public class TodoCompleted implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
    private Integer id;



    /**
     * 任务ID
     */
    private Integer todoId;



    /**
     * 任务状态 0-未开始 1-进行中 2-已完成
     */
    private String status;



    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

}
