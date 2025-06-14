package com.npc.common.modular.reward.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class RewardDto extends PageSearch {

    private Integer id;  // 主键ID 

    private String name;  // 奖励名称 

    private String description;  // 奖励描述（内容或用途说明） 

    private String type;  // 获取方式：任务、积分、金钱、混合 

    private Integer taskId;  // 关联的任务ID（如为任务奖励） 

    private Integer pointsRequired;  // 所需积分（积分兑换） 

    private BigDecimal moneyRequired;  // 所需金钱（¥） 

    private Boolean autoGrant;  // 是否任务完成后自动发放 

    private Integer maxPerUser;  // 每个用户最多兑换次数（NULL 表示无限） 

    private String imageUrl;  // 奖励图标URL 

    private Integer displayOrder;  // 前端展示排序，值越小越靠前 

    private Boolean available;  // 是否在前端可见 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
