package com.npc.common.modular.review.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 每周复盘-计划任务表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Data
@TableName("t_review_weekly_task")
public class ReviewWeeklyTask implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "主键ID")
    private Long id;



    /**
     * 所属周复盘ID
     */
    // @ApiModelProperty(value = "所属周复盘ID")
    private Long weeklyId;



    /**
     * 任务标题
     */
    // @ApiModelProperty(value = "任务标题")
    private String taskTitle;



    /**
     * 任务优先级
     */
    // @ApiModelProperty(value = "任务优先级")
    private String priority;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

}
