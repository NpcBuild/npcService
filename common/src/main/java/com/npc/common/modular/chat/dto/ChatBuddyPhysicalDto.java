package com.npc.common.modular.chat.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 人物身体与外貌特征
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
public class ChatBuddyPhysicalDto extends PageSearch {

    private Integer id; 

    private Integer buddyId; 

    private Integer heightCm;  // 身高(cm) 

    private BigDecimal weightKg;  // 体重(kg) 

    private String bodyShape;  // 体型 

    private String appearanceDesc;  // 外貌描述 

    private String healthNotes;  // 健康状况备注 

    private LocalDateTime createdAt; 

}
