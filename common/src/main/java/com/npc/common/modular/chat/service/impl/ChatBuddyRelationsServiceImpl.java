package com.npc.common.modular.chat.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ChatBuddyRelationsDto;


import com.npc.common.modular.chat.entity.ChatBuddyRelations;
import com.npc.common.modular.chat.mapper.ChatBuddyRelationsMapper;
import com.npc.common.modular.chat.service.IChatBuddyRelationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * <p>
 * 朋友关系 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-11-26
 */
@Service
public class ChatBuddyRelationsServiceImpl extends ServiceImpl<ChatBuddyRelationsMapper, ChatBuddyRelations> implements IChatBuddyRelationsService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyRelationsServiceImpl.class);


    // 计算边的权重
    public double calculateEdgeWeight(Integer intimacyLevel) {
        if (intimacyLevel == null) return 1.0;
        // 将亲密度(0-100)映射到权重(0.5-2.0)
        return 0.5 + (intimacyLevel / 100.0) * 1.5;
    }

    // 获取用户的关系分类
    public String getRelationCategory(Integer userId, List<ChatBuddyRelations> relations) {
        for (ChatBuddyRelations relation : relations) {
            if (relation.getFromId().equals(userId) || relation.getToId().equals(userId)) {
                if (relation.getTypeIds() != null) {
                    return relation.getTypeIds(); // 使用关系类型作为分类
                }
            }
        }
        return "unknown";
    }

    // 根据关系类型获取颜色标签
    public String getColorTag(Integer userId, List<ChatBuddyRelations> relations) {
        String category = getRelationCategory(userId, relations);
        switch (category) {
            case "family":
                return "family";
            case "friend":
                return "friend";
            case "colleague":
                return "colleague";
            default:
                return "other";
        }
    }
}
