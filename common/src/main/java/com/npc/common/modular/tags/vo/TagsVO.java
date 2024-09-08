package com.npc.common.modular.tags.vo;

import lombok.Data;

import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2024/7/2 19:35
 */
@Data
public class TagsVO {
    private Integer id;
    private Integer parentId; // 父级ID
    private Integer groupId; // 组ID
    private String name; // 名称
    private List<TagsVO> children; // 子节点
}
