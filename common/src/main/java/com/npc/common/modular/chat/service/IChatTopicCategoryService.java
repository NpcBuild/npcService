package com.npc.common.modular.chat.service;

import com.npc.common.modular.chat.dto.ChatTopicCategoryDto;
import com.npc.common.modular.chat.entity.ChatTopicCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.chat.vo.CategoryTreeVo;

import java.util.List;

/**
 * <p>
 * 聊天话题分类表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-05-05
 */
public interface IChatTopicCategoryService extends IService<ChatTopicCategory> {
    List<CategoryTreeVo> getTree(ChatTopicCategoryDto chatTopicCategoryDto);
}
