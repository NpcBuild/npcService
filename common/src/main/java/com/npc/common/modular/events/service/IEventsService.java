package com.npc.common.modular.events.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.events.dto.EventsDto;
import com.npc.common.modular.events.entity.Events;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.events.vo.EventsMonthInfoVO;
import com.npc.common.modular.holiday.vo.CalendarEventVO;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.common.modular.problem.vo.ProblemVO;

import java.util.List;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.EventsDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-27
 */
public interface IEventsService extends IService<Events> {
    List<CalendarEventVO> getCalendarEventList(EventsDto eventsDto);

    CalendarEventVO addCalendarEvent(CalendarEventVO eventVO);

    List<CalendarEventVO> getList(EventsDto eventsDto);

    IPage<Events> selectListByPage(EventsDto eventsDto);

    EventsMonthInfoVO getMonthInfo(String planField, int year);
}
