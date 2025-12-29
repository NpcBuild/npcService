package com.npc.common.modular.events.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.chat.service.IChatBuddyService;
import com.npc.common.modular.events.dto.EventsDto;
import com.npc.common.modular.holiday.entity.Holiday;
import com.npc.common.modular.holiday.service.IHolidayService;
import com.npc.common.modular.holiday.vo.CalendarEventVO;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.common.modular.problem.vo.ProblemVO;
import com.npc.common.modular.subscribe.entity.Subscribe;
import com.npc.common.modular.subscribe.service.ISubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.EventsDto;


import com.npc.common.modular.events.entity.Events;
import com.npc.common.modular.events.mapper.EventsMapper;
import com.npc.common.modular.events.service.IEventsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-27
 */
@Service
public class EventsServiceImpl extends ServiceImpl<EventsMapper, Events> implements IEventsService {

    private static final Logger logger = LoggerFactory.getLogger(EventsServiceImpl.class);

    @Autowired
    private IHolidayService holidayService;
    @Autowired
    private IChatBuddyService chatBuddyService;
    @Autowired
    private ISubscribeService subscribeService;
    @Resource
    private EventsMapper eventsMapper;

    @Override
    public List<CalendarEventVO> getList(EventsDto eventsDto) {
        return eventsMapper.getListByRange(eventsDto);
    }

    @Override
    public List<CalendarEventVO> getCalendarEventList(EventsDto eventsDto) {
        // 查询节假日、生日、纪念日、任务、提醒、还款
        List<CalendarEventVO> holidays = holidayService.getList(eventsDto.getStartDate(), eventsDto.getEndDate());
        List<CalendarEventVO> birthdays = chatBuddyService.getBirthdayList(eventsDto);
        List<CalendarEventVO> events = this.getList(eventsDto);
        List<CalendarEventVO> subscribes = subscribeService.getList(eventsDto);

        List<CalendarEventVO> res = new ArrayList<>();
        res.addAll(holidays);
        res.addAll(birthdays);
        res.addAll(events);
        res.addAll(subscribes);
        return res;
    }

    @Override
    public CalendarEventVO addCalendarEvent(CalendarEventVO eventVO) {
        switch (eventVO.getEventType()) {
            case "holiday":
                eventVO.setColor("#FF0000");
                Holiday holiday = new Holiday();
                holiday.setDate(eventVO.getDate());
                holiday.setName(eventVO.getTitle());
                holiday.setHoliday(true);
                holiday.setWage(1);
                holidayService.saveOrUpdate(holiday);
                break;
            case "birthday":
                eventVO.setColor("#FFA500");
//                chatBuddyService.saveOrUpdate();
                break;
            case "event":
                eventVO.setColor("#0000FF");
                Events events = new Events();
                events.setName(eventVO.getTitle());
                events.setEventDate(LocalDateTime.parse(eventVO.getDate()));
//                events.setType(eventVO.getEventType());
                events.setDescription(eventVO.getDescription());
                events.setReminder(eventVO.isImportant());
                this.saveOrUpdate(events);
                break;
            case "subscribe":
                eventVO.setColor("#00FF00");
                Subscribe subscribe = new Subscribe();
                subscribe.setSubContent(eventVO.getTitle());
                subscribe.setPaymentDate(eventVO.getDate());
//                subscribe.setPaymentCycle();
//                subscribe.setSubAmount();
//                subscribe.setCategory();
                subscribe.setSubStatus("1");
                subscribe.setNotes(eventVO.getDescription());
                subscribe.setUserId(1);
                subscribeService.saveOrUpdate(subscribe);
                break;
            default:
                eventVO.setColor("#000000");
                break;
        }
        return eventVO;
    }

    /**获取列表分页*/
    @Override
    public IPage<Events> selectListByPage(EventsDto eventsDto) {
        // 创建分页对象
        Page<Events> page = new Page<>(eventsDto.getPageNum(), eventsDto.getPageSize());

        IPage<Events> list = this.baseMapper.getList(eventsDto, page);
        return list;
    }
}
