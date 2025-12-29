package com.npc.common.modular.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ChatBuddyPhysicalDto;


import com.npc.common.modular.chat.entity.ChatBuddyPhysical;
import com.npc.common.modular.chat.mapper.ChatBuddyPhysicalMapper;
import com.npc.common.modular.chat.service.IChatBuddyPhysicalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 人物身体与外貌特征 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Service
public class ChatBuddyPhysicalServiceImpl extends ServiceImpl<ChatBuddyPhysicalMapper, ChatBuddyPhysical> implements IChatBuddyPhysicalService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyPhysicalServiceImpl.class);
}
