package com.npc.common.modular.dailySchedule.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.dailySchedule.dto.DailyScheduleDto;
import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;

/**
 * <p>
 * 每日日程安排 服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-18
 */
public interface IDailyScheduleService extends IService<DailySchedule> {
    IPage<DailyScheduleVO> selectListByPage(DailyScheduleDto dailyScheduleDto);

}
