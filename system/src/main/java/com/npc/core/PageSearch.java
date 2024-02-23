package com.npc.core;

import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/9/7 21:34
 */
@Data
public class PageSearch {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
