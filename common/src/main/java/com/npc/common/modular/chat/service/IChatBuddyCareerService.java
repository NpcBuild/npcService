package com.npc.common.modular.chat.service;

import com.npc.common.modular.chat.entity.ChatBuddyCareer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.ChatBuddyCareerDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 人物-职业信息 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
public interface IChatBuddyCareerService extends IService<ChatBuddyCareer> {
    List<ChatBuddyCareer> getByBuddyId(int id);
}
