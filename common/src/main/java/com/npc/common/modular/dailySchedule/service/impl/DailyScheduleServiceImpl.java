package com.npc.common.modular.dailySchedule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.dailySchedule.dto.DailyScheduleDto;
import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.npc.common.modular.dailySchedule.mapper.DailyScheduleMapper;
import com.npc.common.modular.dailySchedule.service.IDailyScheduleService;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * <p>
 * 每日日程安排 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-18
 */
@Service
public class DailyScheduleServiceImpl extends ServiceImpl<DailyScheduleMapper, DailySchedule> implements IDailyScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(DailyScheduleServiceImpl.class);

    @Override
    public IPage<DailyScheduleVO> selectListByPage(DailyScheduleDto dailyScheduleDto) {
        // 创建分页对象
        Page<DailySchedule> page = new Page<>(dailyScheduleDto.getPageNum(), dailyScheduleDto.getPageSize());
        IPage<DailyScheduleVO> dailyScheduleIPage = this.baseMapper.getList(page, dailyScheduleDto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.CHINESE);
        for (DailyScheduleVO vo : dailyScheduleIPage.getRecords()) {
            if (null != vo.getStartTime()) {
                vo.setStartTimeCN(vo.getStartTime().format(formatter));
            }
            if (null != vo.getEndTime()) {
                vo.setEndTimeCN(vo.getEndTime().format(formatter));
            }
        }
        return dailyScheduleIPage;

//        QueryWrapper queryWrapper = new QueryWrapper<>(dailyScheduleDto);
//        queryWrapper.orderByAsc("start_time");
        // 执行分页查询，将查询结果封装到分页对象中
//        IPage<DailySchedule> dailyScheduleIPage = this.baseMapper.selectPage(page, queryWrapper);
//        // 创建一个新的分页对象用于存放 DailyScheduleVO
//        Page<DailyScheduleVO> voPage = new Page<>(dailyScheduleDto.getPageNum(), dailyScheduleDto.getPageSize());
//        voPage.setTotal(dailyScheduleIPage.getTotal());
//        voPage.setPages(dailyScheduleIPage.getPages());
//
//        // 转换每个 DailySchedule 为 DailyScheduleVO
//        List<DailyScheduleVO> voList = dailyScheduleIPage.getRecords().stream()
//                .map(this::convertToDailyScheduleVO) // 假设 convertToDailyScheduleVO 是一个将 DailySchedule 转换为 DailyScheduleVO 的方法
//                .collect(Collectors.toList());
//
//        voPage.setRecords(voList);
//
//        return voPage;
    }

    // 用于转换 DailySchedule 到 DailyScheduleVO
    private DailyScheduleVO convertToDailyScheduleVO(DailySchedule dailySchedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.CHINESE);
        DailyScheduleVO vo = new DailyScheduleVO();
        // 填充数据
        BeanUtil.copyProperties(dailySchedule,vo);
        if (null != vo.getStartTime()) {
            vo.setStartTimeCN(dailySchedule.getStartTime().format(formatter));
        }
        if (null != vo.getEndTime()) {
            vo.setEndTimeCN(dailySchedule.getEndTime().format(formatter));
        }
        return vo;
    }
}
