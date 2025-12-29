package com.npc.common.modular.review.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 每日复盘表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Data
@TableName("t_review_daily")
public class ReviewDaily implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "主键ID")
    private Long id;



    /**
     * 用户ID
     */
    // @ApiModelProperty(value = "用户ID")
    private Long userId;



    /**
     * 复盘日期
     */
    // @ApiModelProperty(value = "复盘日期")
    private LocalDate reviewDate;



    /**
     * 今日系统核心任务(JSON数组)
     */
    // @ApiModelProperty(value = "今日系统核心任务(JSON数组)")
    private String todayCoreTasks;



    /**
     * 完成度评分1-10
     */
    // @ApiModelProperty(value = "完成度评分1-10")
    private Integer completionScore;



    /**
     * 今日收获(JSON数组)
     */
    // @ApiModelProperty(value = "今日收获(JSON数组)")
    private String gains;



    /**
     * 今日遗憾或未完成原因
     */
    // @ApiModelProperty(value = "今日遗憾或未完成原因")
    private String regret;



    /**
     * 明日核心任务(JSON数组)
     */
    // @ApiModelProperty(value = "明日核心任务(JSON数组)")
    private String tomorrowCoreTasks;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 更新时间
     */
    // @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

}
