package com.npc.common.modular.location.dto;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2024/8/20 23:01
 */
@Data
public class LocationDto extends PageSearch {
    private Long id;

    private String location; // 位置名

    private String coordinates; // 坐标（经纬度）

    private Integer visited; // 是否去过

    private String description; // 描述

    private Integer stars; // 评分（星级）
}
