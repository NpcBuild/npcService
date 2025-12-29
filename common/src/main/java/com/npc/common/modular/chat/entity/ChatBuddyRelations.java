package com.npc.common.modular.chat.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

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
@TableName("t_chat_buddy_relations")
public class ChatBuddyRelations implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 发起关系的用户 ID（如 “我” 添加 “朋友 A”，这里存我的 user_id）
     */
    // @ApiModelProperty(value = "发起关系的用户 ID（如 “我” 添加 “朋友 A”，这里存我的 user_id）")
    private Integer fromId;



    /**
     * 接收关系的用户 ID（如 “朋友 A” 的 user_id）
     */
    // @ApiModelProperty(value = "接收关系的用户 ID（如 “朋友 A” 的 user_id）")
    private Integer toId;



    /**
     * 关系类型
     */
    // @ApiModelProperty(value = "关系类型")
    private String typeIds;



    /**
     * 关系状态，支持 “好友申请” 流程：
0 = 待同意，1 = 已生效，2 = 已拉黑
     */
    // @ApiModelProperty(value = "关系状态，支持 “好友申请” 流程：0 = 待同意，1 = 已生效，2 = 已拉黑")
    private String status;



    /**
     * 关系创建时间（如发起好友申请的时间）
     */
    // @ApiModelProperty(value = "关系创建时间（如发起好友申请的时间）")
    private LocalDateTime createTime;



    /**
     * 关系更新时间（如同意好友、修改关系类型的时间）
     */
    // @ApiModelProperty(value = "关系更新时间（如同意好友、修改关系类型的时间）")
    private LocalDateTime updateTime;

}
