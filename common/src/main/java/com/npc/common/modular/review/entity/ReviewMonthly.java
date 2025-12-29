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
 * 每月复盘表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Data
@TableName("t_review_monthly")
public class ReviewMonthly implements Serializable {

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
     * 年份
     */
    // @ApiModelProperty(value = "年份")
    private Integer reviewYear;



    /**
     * 月份
     */
    // @ApiModelProperty(value = "月份")
    private Integer reviewMonth;



    /**
     * 上月关键词
     */
    // @ApiModelProperty(value = "上月关键词")
    private String lastMonthKeyword;



    /**
     * 上月成就
     */
    // @ApiModelProperty(value = "上月成就")
    private String lastMonthAchievements;



    /**
     * 上月挑战
     */
    // @ApiModelProperty(value = "上月挑战")
    private String lastMonthChallenges;



    /**
     * 本月关键指标说明
     */
    // @ApiModelProperty(value = "本月关键指标说明")
    private String thisMonthKeyMetricNote;



    /**
     * 下月关键词
     */
    // @ApiModelProperty(value = "下月关键词")
    private String nextMonthKeyword;



    /**
     * 下月三大目标
     */
    // @ApiModelProperty(value = "下月三大目标")
    private String nextMonthTopGoals;



    /**
     * 按周任务说明(JSON数组)
     */
    // @ApiModelProperty(value = "按周任务说明(JSON数组)")
    private String weeklyPlanNotes;



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
