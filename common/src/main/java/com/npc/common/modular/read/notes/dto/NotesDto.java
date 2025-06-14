package com.npc.common.modular.read.notes.dto;

import com.npc.core.PageSearch;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
public class NotesDto extends PageSearch {

    private Integer id; 

    private Integer userId;  // 用户 ID 

    private Integer contentId;  // 关联的书籍 / 电影 

    private BigDecimal rating;  // 评分（可选） 

    private String notes;  // 读书 / 观影笔记 

    private LocalDateTime createdAt; 

}
