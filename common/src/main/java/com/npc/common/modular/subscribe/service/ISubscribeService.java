package com.npc.common.modular.subscribe.service;

import com.npc.common.modular.events.dto.EventsDto;
import com.npc.common.modular.holiday.vo.CalendarEventVO;
import com.npc.common.modular.subscribe.dto.SubscribeDto;
import com.npc.common.modular.subscribe.entity.Subscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.subscribe.vo.SubscribeVO;

import java.util.List;

/**
 * <p>
 * 订阅表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-02-24
 */
public interface ISubscribeService extends IService<Subscribe> {
    List<SubscribeVO> getAllSubscribeList(SubscribeDto subscribeDto);
    List<SubscribeVO> getNextSubscribeList(SubscribeDto subscribeDto);

    List<CalendarEventVO> getList(EventsDto eventsDto);
}

