package com.npc.common.modular.message.vo;

import lombok.Data;

/**
 * @author NPC
 * @description 用户消息列表
 * @create 2023/5/2 17:49
 */
@Data
public class UserMessageVO {
    private int id;
    private String avatar;
    private int userId;
    private String userName;
    /**
     * 最新消息内容
     */
    private String text;
    /**
     * 最新消息时间
     */
    private String timestamp;
    /**
     * 是否已读；false-未读 true-已读
     */
    private Boolean readed;
    /**
     * 未读消息数
     */
    private Integer unReadNum;
}
