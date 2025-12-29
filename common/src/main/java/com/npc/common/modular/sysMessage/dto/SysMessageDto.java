package com.npc.common.modular.sysMessage.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 系统消息 / 通知中心表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-18
 */
@Data
public class SysMessageDto extends PageSearch {

    private Integer id;  // 消息ID 

    private Integer userId;  // 接收用户ID（为空表示全体用户） 

    private String title;  // 消息标题 

    private String content;  // 消息正文内容 

    private String messageType;  // 消息类型（system/task/review/asset/ai等） 

    private String bizType;  // 业务子类型（如 daily_review_remind / asset_expire 等） 

    private Integer priority;  // 优先级(1高-5低) 

    private Integer relatedId;  // 关联业务ID 

    private String relatedType;  // 关联业务类型(review_daily) 

    private String status;  // 状态(unread 红点/read 已查看/archived 归档（不再显示）) 

    private String actionUrl;  // 点击跳转链接 

    private Boolean isPinned;  // 是否置顶 

    private Boolean isPopup;  // 是否弹窗提示 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime readAt;  // 阅读时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
