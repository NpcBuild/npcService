package com.npc.common.modular.tags.dto;

import com.npc.core.PageSearch;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author NPC
 * @description
 * @create 2024/7/1 23:02
 */
@Data
public class TagsDto extends PageSearch {
    private Integer id;
    private Integer parentId; // 父级ID
    private Integer groupId; // 组ID
    private Integer level; // 层级
    private String name; // 名称
    private String type; // 类型
    private String description; // 描述
    private Boolean isFeatured; // 是否是推荐
    private Boolean isPublic; // 是否公共可见
    private String aliases; // 别名
}
