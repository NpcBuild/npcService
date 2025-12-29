package com.npc.common.modular.review.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class ReviewWeeklyDto extends PageSearch {

    private Long id;  // 主键ID 

    private Long userId;  // 用户ID 

    private Integer reviewYear;  // 年份 

    private Integer reviewWeek;  // 第几周 

    private String achievements;  // 本周三大成就 

    private String biggestRegret;  // 本周最大遗憾 

    private String summary;  // 本周得失总结 

    private String nextWeekKeyword;  // 下周关键词 

    private String nextWeekCoreGoals;  // 下周三个核心目标 

    private String messageToSelf;  // 给下周自己的话 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
