package com.npc.common.modular.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.chat.vo.BuddyVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
public interface IChatBuddyService extends IService<ChatBuddy> {

    IPage<ChatBuddy> selectListByPage(BuddyVO buddyVO);
}
