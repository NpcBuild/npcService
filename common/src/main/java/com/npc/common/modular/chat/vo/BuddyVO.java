package com.npc.common.modular.chat.vo;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/12/17 16:58
 */
@Data
public class BuddyVO extends PageSearch {
    private Integer id;

    private String name; // 姓名

    private Boolean hasContact; // 是否还有联系

    private String gender; // 性别

    private String tags; // 标签

    private Integer intimacyLevel; // 亲密度

    private String notes; // 备注

    private Integer sort; // 排序
}
