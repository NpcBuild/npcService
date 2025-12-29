package com.npc.common.modular.review.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class ReviewYearlyDto extends PageSearch {

    private Long id;  // 主键ID 

    private Long userId;  // 用户ID 

    private Integer reviewYear;  // 年份 

    private String yearKeyword;  // 年度关键词 

    private String achievements;  // 年度三大成就 

    private String regrets;  // 年度遗憾 

    private String newYearTopGoals;  // 新年三大目标 

    private String q1Plan;  // Q1规划 

    private String q2Plan;  // Q2规划 

    private String q3Plan;  // Q3规划 

    private String q4Plan;  // Q4规划 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
