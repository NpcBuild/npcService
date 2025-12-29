package com.npc.common.modular.chat.vo;

import lombok.Data;

/**
 * @program: npcService
 * @description 社交网络图边数据结构
 * @author: feiyang
 * @create: 2025/12/10 16:45
 **/
@Data
public class SocialGraphEdge {
    private Integer id;
    private Integer sourceID;
    private Integer targetID;
    private Double weight;
    private boolean visible = true;
}
