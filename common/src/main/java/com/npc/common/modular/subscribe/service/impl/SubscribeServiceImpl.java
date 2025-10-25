package com.npc.common.modular.subscribe.service.impl;

import com.npc.common.modular.subscribe.dto.SubscribeDto;
import com.npc.common.modular.subscribe.vo.SubscribeVO;
import com.npc.utils.CronUtils;
import com.npc.utils.DateUtils;
import org.quartz.CronExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npc.common.modular.subscribe.entity.Subscribe;
import com.npc.common.modular.subscribe.mapper.SubscribeMapper;
import com.npc.common.modular.subscribe.service.ISubscribeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订阅表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-02-24
 */
@Service
public class SubscribeServiceImpl extends ServiceImpl<SubscribeMapper, Subscribe> implements ISubscribeService {
    // 3天内提醒
    private static final int REMIND_MILLI_SECOND = 3 * 24 * 60 * 60;

    private static final Logger logger = LoggerFactory.getLogger(SubscribeServiceImpl.class);

    // 定义日期时间格式
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<SubscribeVO> getNextSubscribeList(SubscribeDto subscribeDto) {
        List<Subscribe> enableList = getEnableList();
        List<SubscribeVO> res = new ArrayList<>();
        for (Subscribe subscribe : enableList) {
            String next = CronUtils.getNext(subscribe.getPayCron(), null);
            int differentDaysByMillisecond = DateUtils.differentDaysByMillisecond(DateUtils.getNowDate(), DateUtils.parseDate(next));
            if (differentDaysByMillisecond <= REMIND_MILLI_SECOND) {
                SubscribeVO subscribeVO = new SubscribeVO();
                BeanUtils.copyProperties(subscribe, subscribeVO);
                if (!ObjectUtils.isEmpty(next)) {
                    subscribeVO.setSubTime(LocalDateTime.parse(next, DATE_TIME_FORMATTER));
                    // 计算还需要还款的周期数
//                    Long remainingPeriods = calculateRemainingPeriods(subscribe);
                    Long remainingPeriods = CronUtils.getRemainingExecutions(subscribe.getPayCron(), subscribe.getEndDate());
                    subscribeVO.setRemainingPeriods(remainingPeriods);
                }
                res.add(subscribeVO);
            }
        }
        // 按照还款日期排序
        return res.stream().sorted(Comparator.comparing(SubscribeVO::getSubTime)).collect(Collectors.toList());
    }

    /**
     * 计算剩余还款周期数
     * @param subscribe 订阅信息
     * @return 剩余周期数
     */
    private Long calculateRemainingPeriods(Subscribe subscribe) {
        try {
            // 方案2: 如果有结束时间，计算从现在到结束时间之间的执行次数
            LocalDateTime endLocalDate = subscribe.getEndDate();
            if (endLocalDate != null) {
                Date endDate = DateUtils.parseDate(endLocalDate.format(DATE_TIME_FORMATTER));
                if (endDate.after(new Date())) { // 确保结束时间在当前时间之后
                    return countExecutionsBetweenDates(subscribe.getPayCron(), new Date(), endDate);
                } else {
                    return 0L; // 如果结束时间已经过了，剩余周期数为0
                }
            }

            // 方案3: 如果上述信息都没有，可以考虑设置一个默认最大值或返回null
            return null;
        } catch (Exception e) {
            logger.error("计算剩余周期数失败，subscribeId: {}", subscribe.getId(), e);
            return null;
        }
    }


    /**
     * 计算两个日期间cron表达式的执行次数
     * @param cronExpression cron表达式
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 执行次数
     */
    private Long countExecutionsBetweenDates(String cronExpression, Date startDate, Date endDate) {
        try {
            // 首先尝试修复cron表达式
            String fixedCronExpression = fixCronExpression(cronExpression);

            CronExpression cron = new CronExpression(fixedCronExpression);
            Date current = startDate;
            long count = 0;
            long maxIterations = 10000; // 防止无限循环
            long iterations = 0;

            while (current.before(endDate) && iterations < maxIterations) {
                current = cron.getNextValidTimeAfter(current);
                if (current != null && current.before(endDate)) {
                    count++;
                } else {
                    break;
                }
                iterations++;
            }

            return count;
        } catch (Exception e) {
            logger.error("计算日期间执行次数失败，cron: {}", cronExpression, e);
            return null;
        }
    }

    /**
     * 修复cron表达达式以符合Quartz规范
     * @param cronExpression 原始cron表达式
     * @return 修复后的cron表达式
     */
    private String fixCronExpression(String cronExpression) {
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            return cronExpression;
        }

        String[] parts = cronExpression.trim().split("\\s+");

        // 确保有6个部分
        if (parts.length != 6) {
            return cronExpression; // 无法修复
        }

        // 检查day-of-month(第4个字段)和day-of-week(第6个字段)
        String dayOfMonth = parts[3];
        String dayOfWeek = parts[5];

        // 如果两个字段都不是?或*，则需要修复
        if (!"?".equals(dayOfMonth) && !"*".equals(dayOfMonth) &&
                !"?".equals(dayOfWeek) && !"*".equals(dayOfWeek)) {
            // 优先保留day-of-month，将day-of-week设为?
            parts[5] = "?";
            return String.join(" ", parts);
        }

        return cronExpression;
    }



    private List<Subscribe> getEnableList() {
        String endTime = DateUtils.getTime();
        return this.baseMapper.getEnableList(endTime);
    }
}
