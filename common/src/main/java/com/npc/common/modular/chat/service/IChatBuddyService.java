package com.npc.common.modular.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.chat.vo.BuddyVO;
import com.npc.common.modular.events.dto.EventsDto;
import com.npc.common.modular.holiday.vo.CalendarEventVO;

import java.util.List;
import java.util.Map;

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

    List<CalendarEventVO> getBirthdayList(EventsDto eventsDto);

    List<CalendarEventVO> getLunchBirthday(EventsDto eventsDto);
}
