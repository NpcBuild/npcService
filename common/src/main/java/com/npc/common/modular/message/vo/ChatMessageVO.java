package com.npc.common.modular.message.vo;

import lombok.Data;

/**
 * @author NPC
 * @description 聊天详情
 * @create 2023/5/2 18:59
 */
@Data
public class ChatMessageVO {
    private Integer id;
    private String type;
    private String text;
    private String time;
    private Boolean me;
    private Boolean readed;
}
