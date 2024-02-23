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
@TableName("t_chat_topic")
public class ChatTopic implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 聊天话题唯一标识符，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;



    /**
     * 聊天参与者的唯一标识符
     */
    private Integer buddyId;



    /**
     * 聊天话题
     */
    private String topic;



    /**
     * 聊天话题标签
     */
    private String tags;



    /**
     * 关于聊天话题的附加注释或笔记
     */
    private String notes;



    /**
     * 表示话题是否被使用过
     */
    private Boolean used;

}
