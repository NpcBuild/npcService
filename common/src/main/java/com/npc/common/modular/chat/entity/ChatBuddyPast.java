package com.npc.common.modular.chat.entity;

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
 * 人物过往经历
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
@TableName("t_chat_buddy_past")
public class ChatBuddyPast implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;
    // @ApiModelProperty(value = "buddyId")
    private Integer buddyId;



    /**
     * 事件类型（成长/创伤/高光）
     */
    // @ApiModelProperty(value = "事件类型（成长/创伤/高光）")
    private String eventType;



    /**
     * 时间描述
     */
    // @ApiModelProperty(value = "时间描述")
    private String eventTime;



    /**
     * 事件标题
     */
    // @ApiModelProperty(value = "事件标题")
    private String eventTitle;



    /**
     * 事件详情
     */
    // @ApiModelProperty(value = "事件详情")
    private String eventDescription;



    /**
     * 影响程度 1-5
     */
    // @ApiModelProperty(value = "影响程度 1-5")
    private Integer impactLevel;
    // @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
