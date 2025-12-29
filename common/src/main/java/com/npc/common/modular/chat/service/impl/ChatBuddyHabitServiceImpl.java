package com.npc.common.modular.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ChatBuddyHabitDto;


import com.npc.common.modular.chat.entity.ChatBuddyHabit;
import com.npc.common.modular.chat.mapper.ChatBuddyHabitMapper;
import com.npc.common.modular.chat.service.IChatBuddyHabitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 人物习惯与行为模式 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Service
public class ChatBuddyHabitServiceImpl extends ServiceImpl<ChatBuddyHabitMapper, ChatBuddyHabit> implements IChatBuddyHabitService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyHabitServiceImpl.class);

    @Override
    public List<ChatBuddyHabit> getByBuddyId(int id) {
        List<ChatBuddyHabit> list = baseMapper.getByBuddyId(id);
        return list;
    }
}
