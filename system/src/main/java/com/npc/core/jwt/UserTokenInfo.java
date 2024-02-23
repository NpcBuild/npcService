package com.npc.core.jwt;

import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/4/10 21:43
 */
@Data
public class UserTokenInfo {
    private String userName;
    private Long userId;
}
