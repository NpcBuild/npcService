package com.npc.common.modular.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.npc.common.modular.chat.dto.ChatTopicCategoryDto;
import com.npc.common.modular.chat.vo.CategoryTreeVo;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.chat.entity.ChatTopicCategory;
import com.npc.common.modular.chat.mapper.ChatTopicCategoryMapper;
import com.npc.common.modular.chat.service.IChatTopicCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;

/**
 * <p>
 * 聊天话题分类表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-05-05
 */
@Service
public class ChatTopicCategoryServiceImpl extends ServiceImpl<ChatTopicCategoryMapper, ChatTopicCategory> implements IChatTopicCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ChatTopicCategoryServiceImpl.class);

    @Override
    public List<CategoryTreeVo> getTree(ChatTopicCategoryDto chatTopicCategoryDto) {
        Integer id = chatTopicCategoryDto.getId();
        Integer level = chatTopicCategoryDto.getLevel();
        List<ChatTopicCategory> chatTopicCategoryList = this.getBaseMapper().getTree(id, level);
        return buildTree(chatTopicCategoryList, id);
    }

    public static List<CategoryTreeVo> buildTree(List<ChatTopicCategory> list, Integer rootId) {
        // 数据类型转换
        List<CategoryTreeVo> voList = new ArrayList<>();
        for (ChatTopicCategory tags : list) {
            CategoryTreeVo vo = new CategoryTreeVo();
            BeanUtil.copyProperties(tags, vo);
            voList.add(vo);
        }

        Map<Integer, CategoryTreeVo> nodeMap = new HashMap<>();
        List<CategoryTreeVo> rootNodes = new ArrayList<>();
        for (CategoryTreeVo node : voList) {
            nodeMap.put(node.getId(), node);
            if (Objects.equals(node.getParentId(), rootId)) {
                rootNodes.add(node);
            }
        }

        for (CategoryTreeVo node : voList) {
            CategoryTreeVo parentNode = nodeMap.get(node.getParentId());
            if (parentNode!= null) {
                if (parentNode.getChildren() == null) {
                    List<CategoryTreeVo> tagsVOList = new ArrayList<>();
                    parentNode.setChildren(tagsVOList);
                }
                parentNode.getChildren().add(node);
            }
        }
        return rootNodes;
    }
}
