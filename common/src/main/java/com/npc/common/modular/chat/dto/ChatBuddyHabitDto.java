package com.npc.common.modular.chat.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 人物习惯与行为模式
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
public class ChatBuddyHabitDto extends PageSearch {

    private Integer id; 

    private Integer buddyId; 

    private String habitType;  // 类型（作息/饮食/消费/社交） 

    private String habitName;  // 习惯名称 

    private Integer habitLevel;  // 程度 1-5 

    private String frequency;  // 频率（每天/每周） 

    private String description;  // 行为描述 

    private LocalDateTime createdAt; 

}
