package com.npc.core.limit.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/4/15 21:11
 */
@Data
@Builder
public class Limiter {

    private String key;
    private int seconds;
    private int limitNum;

}
