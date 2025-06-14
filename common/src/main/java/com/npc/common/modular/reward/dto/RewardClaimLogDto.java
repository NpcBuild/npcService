package com.npc.common.modular.reward.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class RewardClaimLogDto extends PageSearch {

    private Integer id;  // 主键ID 

    private Integer userId;  // 兑换人ID 

    private Integer rewardId;  // 对应奖励ID 

    private Integer pointsUsed;  // 使用的积分数量 

    private BigDecimal moneyUsed;  // 使用的金钱金额 

    private String status;  // 兑换状态：待处理/已发放/失败 

    private String note;  // 备注说明，例如失败原因或发放方式 

    private LocalDateTime createdAt;  // 兑换时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
