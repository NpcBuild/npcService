package com.npc.common.modular.holiday.service;

import com.npc.common.modular.holiday.entity.Holiday;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.holiday.vo.CalendarEventVO;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-02-16
 */
public interface IHolidayService extends IService<Holiday> {
    List<CalendarEventVO> getList(LocalDate startDate, LocalDate endDate);
}
