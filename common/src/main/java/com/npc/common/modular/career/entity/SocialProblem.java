package com.npc.common.modular.career.entity;

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
 * 社会问题池
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@Data
@TableName("t_social_problem")
public class SocialProblem implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 问题ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "问题ID")
    private Integer id;



    /**
     * 问题标题
     */
    // @ApiModelProperty(value = "问题标题")
    private String title;



    /**
     * 问题描述
     */
    // @ApiModelProperty(value = "问题描述")
    private String description;



    /**
     * 问题分类
     */
    // @ApiModelProperty(value = "问题分类")
    private String category;



    /**
     * 严重程度(1-5)
     */
    // @ApiModelProperty(value = "严重程度(1-5)")
    private Integer severity;



    /**
     * 受影响人群
     */
    // @ApiModelProperty(value = "受影响人群")
    private String affectedPopulation;



    /**
     * 发生频率描述
     */
    // @ApiModelProperty(value = "发生频率描述")
    private String frequency;



    /**
     * 现有解决方案
     */
    // @ApiModelProperty(value = "现有解决方案")
    private String currentSolutions;



    /**
     * 痛点程度(0-5)
     */
    // @ApiModelProperty(value = "痛点程度(0-5)")
    private Integer pain;



    /**
     * 购买力(0-5)
     */
    // @ApiModelProperty(value = "购买力(0-5)")
    private Integer purchasingPower;



    /**
     * 触达难度(0-5)
     */
    // @ApiModelProperty(value = "触达难度(0-5)")
    private Integer reachability;



    /**
     * 市场增长(0-5)
     */
    // @ApiModelProperty(value = "市场增长(0-5)")
    private Integer marketGrowth;



    /**
     * 总评分(计算字段)
     */
    // @ApiModelProperty(value = "总评分(计算字段)")
    private Integer totalScore;



    /**
     * 标签，逗号分隔
     */
    // @ApiModelProperty(value = "标签，逗号分隔")
    private String tags;



    /**
     * 补充笔记
     */
    // @ApiModelProperty(value = "补充笔记")
    private String notes;



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
