package com.npc.common.modular.events.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // 区间-开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // 区间-结束日期

}
