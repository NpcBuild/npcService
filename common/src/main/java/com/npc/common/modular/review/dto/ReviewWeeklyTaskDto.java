package com.npc.common.modular.review.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 每周复盘-计划任务表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Data
public class ReviewWeeklyTaskDto extends PageSearch {

    private Long id;  // 主键ID 

    private Long weeklyId;  // 所属周复盘ID 

    private String taskTitle;  // 任务标题 

    private String priority;  // 任务优先级 

    private LocalDateTime createdAt;  // 创建时间 

}
