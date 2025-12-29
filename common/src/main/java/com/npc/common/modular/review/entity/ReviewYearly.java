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
 * 年度复盘表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Data
@TableName("t_review_yearly")
public class ReviewYearly implements Serializable {

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
     * 年度关键词
     */
    // @ApiModelProperty(value = "年度关键词")
    private String yearKeyword;



    /**
     * 年度三大成就
     */
    // @ApiModelProperty(value = "年度三大成就")
    private String achievements;



    /**
     * 年度遗憾
     */
    // @ApiModelProperty(value = "年度遗憾")
    private String regrets;



    /**
     * 新年三大目标
     */
    // @ApiModelProperty(value = "新年三大目标")
    private String newYearTopGoals;



    /**
     * Q1规划
     */
    // @ApiModelProperty(value = "Q1规划")
    private String q1Plan;



    /**
     * Q2规划
     */
    // @ApiModelProperty(value = "Q2规划")
    private String q2Plan;



    /**
     * Q3规划
     */
    // @ApiModelProperty(value = "Q3规划")
    private String q3Plan;



    /**
     * Q4规划
     */
    // @ApiModelProperty(value = "Q4规划")
    private String q4Plan;



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
