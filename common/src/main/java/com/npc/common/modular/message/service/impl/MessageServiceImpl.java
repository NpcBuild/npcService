package com.npc.common.modular.message.service.impl;

import com.npc.common.modular.message.entity.Message;
import com.npc.common.modular.message.mapper.MessageMapper;
import com.npc.common.modular.message.service.IMessageService;
import com.npc.common.modular.message.vo.ChatMessageVO;
import com.npc.common.modular.message.vo.UserMessageVO;
import com.npc.common.modular.user.mapper.UserRoleMapper;
import com.npc.common.modular.user.model.result.UserRoleResult;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-05-02
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Resource
    private MessageMapper messageMapper;

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public List<UserMessageVO> findUserMessageByUserId(Long id) {
        return messageMapper.findUserMessageByUserId(id);
    }
    @Override
    public List<ChatMessageVO> findChatMessageById(Long id,Long tId) {
        return messageMapper.findChatMessageById(id,tId);
    }
}
