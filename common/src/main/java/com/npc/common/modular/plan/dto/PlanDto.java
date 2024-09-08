package com.npc.common.modular.plan.dto;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2024/7/20 13:39
 */
@Data
public class PlanDto extends PageSearch {
    private Long id;
    private Integer parentId; // 父级ID
    private String content; // 内容
    private String todoId; // 待办事项ID
    private String remark; // 备注
    private Integer sort; // 排序
    private String tag; // 标签
}
