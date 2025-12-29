package com.npc.common.modular.sysMessage.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("t_sys_message")
public class SysMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "消息ID")
    private Integer id;



    /**
     * 接收用户ID（为空表示全体用户）
     */
    // @ApiModelProperty(value = "接收用户ID（为空表示全体用户）")
    private Integer userId;



    /**
     * 消息标题
     */
    // @ApiModelProperty(value = "消息标题")
    private String title;



    /**
     * 消息正文内容
     */
    // @ApiModelProperty(value = "消息正文内容")
    private String content;



    /**
     * 消息类型（system/task/review/asset/ai等）
     */
    // @ApiModelProperty(value = "消息类型（system/task/review/asset/ai等）")
    private String messageType;



    /**
     * 业务子类型（如 daily_review_remind / asset_expire 等）
     */
    // @ApiModelProperty(value = "业务子类型（如 daily_review_remind / asset_expire 等）")
    private String bizType;



    /**
     * 优先级(1高-5低)
     */
    // @ApiModelProperty(value = "优先级(1高-5低)")
    private Integer priority;



    /**
     * 关联业务ID
     */
    // @ApiModelProperty(value = "关联业务ID")
    private Integer relatedId;



    /**
     * 关联业务类型(review_daily)
     */
    // @ApiModelProperty(value = "关联业务类型(review_daily)")
    private String relatedType;



    /**
     * 状态(unread 红点/read 已查看/archived 归档（不再显示）)
     */
    // @ApiModelProperty(value = "状态(unread 红点/read 已查看/archived 归档（不再显示）)")
    private String status;



    /**
     * 点击跳转链接
     */
    // @ApiModelProperty(value = "点击跳转链接")
    private String actionUrl;



    /**
     * 是否置顶
     */
    // @ApiModelProperty(value = "是否置顶")
    private Boolean isPinned;



    /**
     * 是否弹窗提示
     */
    // @ApiModelProperty(value = "是否弹窗提示")
    private Boolean isPopup;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 阅读时间
     */
    // @ApiModelProperty(value = "阅读时间")
    private LocalDateTime readAt;



    /**
     * 更新时间
     */
    // @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    /**
     * 计算字段：是否已读
     * @return 如果readAt有值则返回true，否则返回false
     */
    public Boolean isRead() {
        return this.readAt != null;
    }
}
