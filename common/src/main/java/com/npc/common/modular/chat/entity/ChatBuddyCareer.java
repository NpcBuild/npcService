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
 * 人物-职业信息
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
@TableName("t_chat_buddy_career")
public class ChatBuddyCareer implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 人物ID
     */
    // @ApiModelProperty(value = "人物ID")
    private Integer buddyId;



    /**
     * 职业
     */
    // @ApiModelProperty(value = "职业")
    private String occupation;



    /**
     * 行业
     */
    // @ApiModelProperty(value = "行业")
    private String industry;



    /**
     * 公司/组织
     */
    // @ApiModelProperty(value = "公司/组织")
    private String company;



    /**
     * 职位
     */
    // @ApiModelProperty(value = "职位")
    private String position;



    /**
     * 收入水平（低/中/高）
     */
    // @ApiModelProperty(value = "收入水平（低/中/高）")
    private String incomeLevel;



    /**
     * 工作方式（自由/稳定/高压）
     */
    // @ApiModelProperty(value = "工作方式（自由/稳定/高压）")
    private String workStyle;



    /**
     * 职业补充说明
     */
    // @ApiModelProperty(value = "职业补充说明")
    private String notes;
    // @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
