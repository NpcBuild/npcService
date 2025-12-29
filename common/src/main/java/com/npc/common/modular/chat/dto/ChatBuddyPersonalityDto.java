package com.npc.common.modular.chat.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 人物性格与人格特征
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
public class ChatBuddyPersonalityDto extends PageSearch {

    private Integer id; 

    private Integer buddyId; 

    private String traitName;  // 性格特质（诚实/自律/勇敢） 

    private Integer traitLevel;  // 强度 1-5 

    private String description;  // 具体表现 

    private LocalDateTime createdAt; 

}
