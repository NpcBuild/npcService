package com.npc.common.modular.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ChatBuddyPastDto;


import com.npc.common.modular.chat.entity.ChatBuddyPast;
import com.npc.common.modular.chat.mapper.ChatBuddyPastMapper;
import com.npc.common.modular.chat.service.IChatBuddyPastService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 人物过往经历 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Service
public class ChatBuddyPastServiceImpl extends ServiceImpl<ChatBuddyPastMapper, ChatBuddyPast> implements IChatBuddyPastService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyPastServiceImpl.class);

    @Override
    public List<ChatBuddyPast> getByBuddyId(int id) {
        List<ChatBuddyPast> list = baseMapper.getByBuddyId(id);
        return list;
    }
}
