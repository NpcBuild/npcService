package com.npc.common.modular.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@Data
@TableName("t_chat_buddy")
public class ChatBuddy implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 聊天参与者唯一标识符，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;



    /**
     * 聊天参与者姓名
     */
    private String name;



    /**
     * base64编码头像
     */
    private String base64Icon;



    /**
     * 是否还有联系
     */
    private Boolean hasContact;



    /**
     * 性别
     */
    private String gender;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 居住地址ID（关联 t_location.id）
     */
    private Integer locationId;



    /**
     * 标签
     */
    private String tags;



    /**
     * 亲密度
     */
    private Integer intimacyLevel;

    /**
     * 阳历生日
     */
    // @ApiModelProperty(value = "阳历生日")
    private String gregorianBirthday;



    /**
     * 农历生日
     */
    // @ApiModelProperty(value = "农历生日")
    private String lunarBirthday;


    /**
     * 备注
     */
    private String notes;

    /**
     * 排序
     */
    private Integer sort;



    /**
     * 简介
     */
    // @ApiModelProperty(value = "简介")
    private String bio;



    /**
     * 头像
     */
    // @ApiModelProperty(value = "头像")
    private String avatar;



    /**
     * 照片url列表
     */
    // @ApiModelProperty(value = "照片url列表")
    private String photos;



    /**
     * 星标
     */
    // @ApiModelProperty(value = "星标")
    private Integer favorite;



    /**
     * 性格
     */
    // @ApiModelProperty(value = "性格")
    private String disposition;

    // 关系
    @TableField(exist = false)
    private String relation;
}
