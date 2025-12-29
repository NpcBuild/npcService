package com.npc.common.modular.holiday.service.impl;

import com.npc.common.modular.holiday.vo.CalendarEventVO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.holiday.entity.Holiday;
import com.npc.common.modular.holiday.mapper.HolidayMapper;
import com.npc.common.modular.holiday.service.IHolidayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-02-16
 */
@Service
public class HolidayServiceImpl extends ServiceImpl<HolidayMapper, Holiday> implements IHolidayService {

    private static final Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);

    @Resource
    private HolidayMapper holidayMapper;

    @Override
    public List<CalendarEventVO> getList(LocalDate startDate, LocalDate endDate) {
        return holidayMapper.getList(startDate, endDate);
    }
}
