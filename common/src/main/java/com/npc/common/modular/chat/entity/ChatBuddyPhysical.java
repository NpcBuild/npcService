package com.npc.common.modular.chat.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@TableName("t_chat_buddy_physical")
public class ChatBuddyPhysical implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;
    // @ApiModelProperty(value = "buddyId")
    private Integer buddyId;



    /**
     * 身高(cm)
     */
    // @ApiModelProperty(value = "身高(cm)")
    private Integer heightCm;



    /**
     * 体重(kg)
     */
    // @ApiModelProperty(value = "体重(kg)")
    private BigDecimal weightKg;



    /**
     * 体型
     */
    // @ApiModelProperty(value = "体型")
    private String bodyShape;



    /**
     * 外貌描述
     */
    // @ApiModelProperty(value = "外貌描述")
    private String appearanceDesc;



    /**
     * 健康状况备注
     */
    // @ApiModelProperty(value = "健康状况备注")
    private String healthNotes;
    // @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
