package com.npc.common.modular.chat.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: npcService
 * @description 社交网络图节点数据结构
 * @author: feiyang
 * @create: 2025/12/10 16:43
 **/
@Data
public class SocialGraphNode {
    private Integer id;
    private String label;
    private Double x;
    private Double y;
    private Double vx;  // velocity x
    private Double vy;  // velocity y
    private Double mass;
    private Integer radius;
    private Map<String, Object> metadata;
    private String colorTag;
    private List<Integer> childrenIDs = new ArrayList<>();
    private Boolean isLocked = false;
    private Boolean isExpanded = false;
    private Boolean isVisible = true;
    private Boolean isHighlighted = false;
}
