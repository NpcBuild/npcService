package com.npc.common.modular.reward.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 奖励定义表
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Data
public class Reward implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "主键ID")
    private Integer id;



    /**
     * 奖励名称
     */
    // @ApiModelProperty(value = "奖励名称")
    private String name;



    /**
     * 奖励描述（内容或用途说明）
     */
    // @ApiModelProperty(value = "奖励描述（内容或用途说明）")
    private String description;



    /**
     * 获取方式：任务、积分、金钱、混合
     */
    // @ApiModelProperty(value = "获取方式：任务、积分、金钱、混合")
    private String type;



    /**
     * 关联的任务ID（如为任务奖励）
     */
    // @ApiModelProperty(value = "关联的任务ID（如为任务奖励）")
    private Integer taskId;



    /**
     * 所需积分（积分兑换）
     */
    // @ApiModelProperty(value = "所需积分（积分兑换）")
    private Integer pointsRequired;



    /**
     * 所需金钱（¥）
     */
    // @ApiModelProperty(value = "所需金钱（¥）")
    private BigDecimal moneyRequired;



    /**
     * 是否任务完成后自动发放
     */
    // @ApiModelProperty(value = "是否任务完成后自动发放")
    private Boolean autoGrant;



    /**
     * 每个用户最多兑换次数（NULL 表示无限）
     */
    // @ApiModelProperty(value = "每个用户最多兑换次数（NULL 表示无限）")
    private Integer maxPerUser;



    /**
     * 奖励图标URL
     */
    // @ApiModelProperty(value = "奖励图标URL")
    private String imageUrl;



    /**
     * 前端展示排序，值越小越靠前
     */
    // @ApiModelProperty(value = "前端展示排序，值越小越靠前")
    private Integer displayOrder;



    /**
     * 是否在前端可见
     */
    // @ApiModelProperty(value = "是否在前端可见")
    private Boolean available;



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
