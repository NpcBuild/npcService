package com.npc.core.jwt;

import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/4/10 21:39
 */
@Data
public class UserToken {
    private String accessToken;
    // 刷新token
    private String refreshToken;
}
