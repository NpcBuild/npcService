package com.npc.common.modular.events.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-06-27
 */
@Data
public class EventsDto extends PageSearch {

    private Integer id; 

    private String name;  // 事件名称 

    private LocalDateTime eventDate;  // 事件日期 

    private String type;  // 事件类型：倒计时或纪念日 

    private String description;  // 事件描述 

    private Boolean reminder;  // 是否提醒 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
