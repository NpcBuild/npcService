package com.npc.common.modular.feedback.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 用户反馈信息表
 * </p>
 *
 * @author yangfei
 * @since 2025-11-06
 */
@Data
public class SysFeedbackDto extends PageSearch {

    private Integer id;  // 反馈唯一标识，自增长 

    private Integer userId;  // 用户ID，可关联用户表 

    private String feedbackType;  // 反馈类型：BUG=功能问题，SUGGESTION=功能建议，UI=界面优化，PERFORMANCE=性能问题，OTHER=其他 

    private String title;  // 反馈标题，简短描述问题 

    private String content;  // 详细反馈内容 

    private String screenshotUrls;  // 截图或图片链接（JSON数组形式） 

    private String contactInfo;  // 联系方式（邮箱/手机号/微信等） 

    private String deviceInfo;  // 设备信息（手机型号、浏览器、操作系统等） 

    private String appVersion;  // App或网站版本号 

    private String status;  // 处理状态：待处理、处理中、已解决、已关闭 

    private String adminReply;  // 管理员回复或处理说明 

    private String priority;  // 问题优先级 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
