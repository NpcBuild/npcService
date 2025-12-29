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
 * 人物性格与人格特征
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
@TableName("t_chat_buddy_personality")
public class ChatBuddyPersonality implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;
    // @ApiModelProperty(value = "buddyId")
    private Integer buddyId;



    /**
     * 性格特质（诚实/自律/勇敢）
     */
    // @ApiModelProperty(value = "性格特质（诚实/自律/勇敢）")
    private String traitName;



    /**
     * 强度 1-5
     */
    // @ApiModelProperty(value = "强度 1-5")
    private Integer traitLevel;



    /**
     * 具体表现
     */
    // @ApiModelProperty(value = "具体表现")
    private String description;
    // @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
