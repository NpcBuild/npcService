package com.npc.common.modular.chat.service;

import com.npc.common.modular.chat.entity.ChatBuddyPast;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.ChatBuddyPastDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 人物过往经历 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
public interface IChatBuddyPastService extends IService<ChatBuddyPast> {
    List<ChatBuddyPast> getByBuddyId(int id);
}
