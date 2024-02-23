package com.npc.common.modular.chat.vo;

import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/12/17 16:58
 */
@Data
public class TopicVO {

    private Integer id;

    private Integer buddyId; // 聊天参与者的唯一标识符

    private String topic; // 话题

    private String tags; // 标签

    private String notes; // 附加注释或笔记

    private Boolean used; // 被使用过

}
