package com.npc.common.modular.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.message.entity.Message;
import com.npc.common.modular.message.vo.ChatMessageVO;
import com.npc.common.modular.message.vo.UserMessageVO;
import com.npc.common.modular.user.model.result.UserRoleResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-05-02
 */
public interface IMessageService extends IService<Message> {
    List<UserMessageVO> findUserMessageByUserId(Long id);
    List<ChatMessageVO> findChatMessageById(Long id,Long tId);
}
