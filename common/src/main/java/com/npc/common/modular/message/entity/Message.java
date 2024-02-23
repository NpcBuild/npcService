package com.npc.common.modular.message.entity;

import java.io.Serializable;


import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2023-05-02
 */
@Data
@TableName("t_message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 消息内容
     */
    private String text;

    /**
     * 发送时间戳
     */
    private Long timestamp;

    /**
     * 发送者ID
     */
    private Integer senderId;

    /**
     * 接收者ID
     */
    private Integer receiverId;

    /**
     * 消息类型
     */
    private String type;
    /**
     * 是否已读；false-未读 true-已读
     */
    private Boolean readed;

}
