package com.npc.common.modular.reward.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 奖励兑换记录表
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Data
@TableName("reward_claim_log")
public class RewardClaimLog implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "主键ID")
    private Integer id;



    /**
     * 兑换人ID
     */
    // @ApiModelProperty(value = "兑换人ID")
    private Long userId;



    /**
     * 对应奖励ID
     */
    // @ApiModelProperty(value = "对应奖励ID")
    private Integer rewardId;



    /**
     * 使用的积分数量
     */
    // @ApiModelProperty(value = "使用的积分数量")
    private Integer pointsUsed;



    /**
     * 使用的金钱金额
     */
    // @ApiModelProperty(value = "使用的金钱金额")
    private BigDecimal moneyUsed;



    /**
     * 兑换状态：待处理/已发放/失败
     */
    // @ApiModelProperty(value = "兑换状态：待处理/已发放/失败")
    private String status;



    /**
     * 备注说明，例如失败原因或发放方式
     */
    // @ApiModelProperty(value = "备注说明，例如失败原因或发放方式")
    private String note;



    /**
     * 兑换时间
     */
    // @ApiModelProperty(value = "兑换时间")
    private LocalDateTime createdAt;



    /**
     * 更新时间
     */
    // @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

}
