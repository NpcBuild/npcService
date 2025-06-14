package com.npc.common.modular.chat.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 聊天话题分类表
 * </p>
 *
 * @author yangfei
 * @since 2025-05-05
 */
@Data
public class ChatTopicCategoryDto extends PageSearch {

    private Integer id;  // 主键 

    private Integer parentId;  // 父级分类ID，0为顶级分类 

    private String name;  // 分类名称 

    private String description;  // 分类描述，可选

    private String toDo;  // 怎么做
    private Boolean important;  // 是否重要

    private Integer sortOrder;  // 排序字段，越小越靠前 

    private Boolean isLeaf;  // 是否为叶子节点（即不能再细分） 

    private Boolean status;  // 状态（1:启用，0:禁用） 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间

    private Integer level; // 查询层级

}
