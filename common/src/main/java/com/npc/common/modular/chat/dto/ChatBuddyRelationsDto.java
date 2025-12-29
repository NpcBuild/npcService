package com.npc.common.modular.chat.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 朋友关系
 * </p>
 *
 * @author yangfei
 * @since 2025-11-26
 */
@Data
public class ChatBuddyRelationsDto extends PageSearch {

    private Integer id; 

    private Integer fromId;  // 发起关系的用户 ID（如 “我” 添加 “朋友 A”，这里存我的 user_id） 

    private Integer toId;  // 接收关系的用户 ID（如 “朋友 A” 的 user_id） 

    private String typeIds;  // 关系类型 

    private String status;  // 关系状态，支持 “好友申请” 流程：0 = 待同意，1 = 已生效，2 = 已拉黑

    private LocalDateTime createTime;  // 关系创建时间（如发起好友申请的时间） 

    private LocalDateTime updateTime;  // 关系更新时间（如同意好友、修改关系类型的时间） 

}
