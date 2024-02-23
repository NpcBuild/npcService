package com.npc.common.modular.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
     * 是否还有联系
     */
    private Boolean hasContact;



    /**
     * 性别
     */
    private String gender;



    /**
     * 标签
     */
    private String tags;



    /**
     * 亲密度
     */
    private Integer intimacyLevel;



    /**
     * 备注
     */
    private String notes;

    /**
     * 排序
     */
    private Integer sort;
}
