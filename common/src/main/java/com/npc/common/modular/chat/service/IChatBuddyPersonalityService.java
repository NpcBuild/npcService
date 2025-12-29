package com.npc.common.modular.chat.service;

import com.npc.common.modular.chat.entity.ChatBuddyPersonality;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.ChatBuddyPersonalityDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 人物性格与人格特征 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
public interface IChatBuddyPersonalityService extends IService<ChatBuddyPersonality> {
    List<ChatBuddyPersonality> getByBuddyId(int id);
}
