package com.npc.common.modular.events.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.EventsDto;


import com.npc.common.modular.events.entity.Events;
import com.npc.common.modular.events.mapper.EventsMapper;
import com.npc.common.modular.events.service.IEventsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
}
