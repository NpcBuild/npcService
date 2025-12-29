package com.npc.common.modular.review.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class ReviewMonthlyDto extends PageSearch {

    private Long id;  // 主键ID 

    private Long userId;  // 用户ID 

    private Integer reviewYear;  // 年份 

    private Integer reviewMonth;  // 月份 

    private String lastMonthKeyword;  // 上月关键词 

    private String lastMonthAchievements;  // 上月成就 

    private String lastMonthChallenges;  // 上月挑战 

    private String thisMonthKeyMetricNote;  // 本月关键指标说明 

    private String nextMonthKeyword;  // 下月关键词 

    private String nextMonthTopGoals;  // 下月三大目标 

    private String weeklyPlanNotes;  // 按周任务说明(JSON数组) 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
