package com.npc.common.modular.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ChatBuddyCareerDto;


import com.npc.common.modular.chat.entity.ChatBuddyCareer;
import com.npc.common.modular.chat.mapper.ChatBuddyCareerMapper;
import com.npc.common.modular.chat.service.IChatBuddyCareerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 人物-职业信息 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Service
public class ChatBuddyCareerServiceImpl extends ServiceImpl<ChatBuddyCareerMapper, ChatBuddyCareer> implements IChatBuddyCareerService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyCareerServiceImpl.class);

    @Override
    public List<ChatBuddyCareer> getByBuddyId(int id) {
        List<ChatBuddyCareer> list = baseMapper.getByBuddyId(id);
        return list;
    }
}
