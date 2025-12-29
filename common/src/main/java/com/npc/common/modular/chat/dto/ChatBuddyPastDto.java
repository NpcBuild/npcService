package com.npc.common.modular.chat.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 人物过往经历
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
public class ChatBuddyPastDto extends PageSearch {

    private Integer id; 

    private Integer buddyId; 

    private String eventType;  // 事件类型（成长/创伤/高光） 

    private String eventTime;  // 时间描述 

    private String eventTitle;  // 事件标题 

    private String eventDescription;  // 事件详情 

    private Integer impactLevel;  // 影响程度 1-5 

    private LocalDateTime createdAt; 

}
