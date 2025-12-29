package com.npc.common.modular.chat.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 人物-职业信息
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
public class ChatBuddyCareerDto extends PageSearch {

    private Integer id; 

    private Integer buddyId;  // 人物ID 

    private String occupation;  // 职业 

    private String industry;  // 行业 

    private String company;  // 公司/组织 

    private String position;  // 职位 

    private String incomeLevel;  // 收入水平（低/中/高） 

    private String workStyle;  // 工作方式（自由/稳定/高压） 

    private String notes;  // 职业补充说明 

    private LocalDateTime createdAt; 

}
