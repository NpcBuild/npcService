package com.npc.common.modular.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.chat.entity.ChatTopic;
import com.npc.common.modular.chat.mapper.ChatTopicMapper;
import com.npc.common.modular.chat.service.IChatTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@Service
public class ChatTopicServiceImpl extends ServiceImpl<ChatTopicMapper, ChatTopic> implements IChatTopicService {

    private static final Logger logger = LoggerFactory.getLogger(ChatTopicServiceImpl.class);
}
