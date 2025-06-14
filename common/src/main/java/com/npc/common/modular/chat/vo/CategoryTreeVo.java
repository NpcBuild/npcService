package com.npc.common.modular.chat.vo;

import com.npc.common.modular.chat.entity.ChatTopicCategory;
import lombok.Data;

import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2025/5/5 21:00
 */
@Data
public class CategoryTreeVo extends ChatTopicCategory {
    private List<CategoryTreeVo> children;
}
