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
 * 每周复盘表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Data
@TableName("t_review_weekly")
public class ReviewWeekly implements Serializable {

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
     * 第几周
     */
    // @ApiModelProperty(value = "第几周")
    private Integer reviewWeek;



    /**
     * 本周三大成就
     */
    // @ApiModelProperty(value = "本周三大成就")
    private String achievements;



    /**
     * 本周最大遗憾
     */
    // @ApiModelProperty(value = "本周最大遗憾")
    private String biggestRegret;



    /**
     * 本周得失总结
     */
    // @ApiModelProperty(value = "本周得失总结")
    private String summary;



    /**
     * 下周关键词
     */
    // @ApiModelProperty(value = "下周关键词")
    private String nextWeekKeyword;



    /**
     * 下周三个核心目标
     */
    // @ApiModelProperty(value = "下周三个核心目标")
    private String nextWeekCoreGoals;



    /**
     * 给下周自己的话
     */
    // @ApiModelProperty(value = "给下周自己的话")
    private String messageToSelf;



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
