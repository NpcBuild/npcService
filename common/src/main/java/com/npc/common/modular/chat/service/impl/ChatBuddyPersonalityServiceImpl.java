package com.npc.common.modular.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ChatBuddyPersonalityDto;


import com.npc.common.modular.chat.entity.ChatBuddyPersonality;
import com.npc.common.modular.chat.mapper.ChatBuddyPersonalityMapper;
import com.npc.common.modular.chat.service.IChatBuddyPersonalityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 人物性格与人格特征 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Service
public class ChatBuddyPersonalityServiceImpl extends ServiceImpl<ChatBuddyPersonalityMapper, ChatBuddyPersonality> implements IChatBuddyPersonalityService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyPersonalityServiceImpl.class);

    @Override
    public List<ChatBuddyPersonality> getByBuddyId(int id) {
        List<ChatBuddyPersonality> list = baseMapper.getByBuddyId(id);
        return list;
    }
}
