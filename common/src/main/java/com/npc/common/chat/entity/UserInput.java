package com.npc.common.chat.entity;

import lombok.Data;

/**
 * @author NPC
 * @description 用户问题输入
 * @create 2023/3/26 15:11
 */
@Data
public class UserInput {
    private Long id;
    private String text;
    private String response;
}
