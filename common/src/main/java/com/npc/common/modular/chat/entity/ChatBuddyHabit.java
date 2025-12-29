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
 * 人物习惯与行为模式
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
@TableName("t_chat_buddy_habit")
public class ChatBuddyHabit implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;
    // @ApiModelProperty(value = "buddyId")
    private Integer buddyId;



    /**
     * 类型（作息/饮食/消费/社交）
     */
    // @ApiModelProperty(value = "类型（作息/饮食/消费/社交）")
    private String habitType;



    /**
     * 习惯名称
     */
    // @ApiModelProperty(value = "习惯名称")
    private String habitName;



    /**
     * 程度 1-5
     */
    // @ApiModelProperty(value = "程度 1-5")
    private Integer habitLevel;



    /**
     * 频率（每天/每周）
     */
    // @ApiModelProperty(value = "频率（每天/每周）")
    private String frequency;



    /**
     * 行为描述
     */
    // @ApiModelProperty(value = "行为描述")
    private String description;
    // @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
