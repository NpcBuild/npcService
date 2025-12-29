package com.npc.common.modular.review.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 每日复盘表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Data
public class ReviewDailyDto extends PageSearch {

    private Long id;  // 主键ID 

    private Long userId;  // 用户ID 

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reviewDate;  // 复盘日期 

    private String todayCoreTasks;  // 今日系统核心任务(JSON数组) 

    private Integer completionScore;  // 完成度评分1-10 

    private String gains;  // 今日收获(JSON数组) 

    private String regret;  // 今日遗憾或未完成原因 

    private String tomorrowCoreTasks;  // 明日核心任务(JSON数组) 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
