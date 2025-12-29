package com.npc.common.modular.chat.service;

import com.npc.common.modular.chat.entity.ChatBuddyHabit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.ChatBuddyHabitDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 人物习惯与行为模式 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
public interface IChatBuddyHabitService extends IService<ChatBuddyHabit> {
    List<ChatBuddyHabit> getByBuddyId(int id);
}
