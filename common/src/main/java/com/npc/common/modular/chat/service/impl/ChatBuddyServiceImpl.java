package com.npc.common.modular.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.npc.common.modular.chat.mapper.ChatBuddyMapper;
import com.npc.common.modular.chat.service.IChatBuddyService;
import com.npc.common.modular.chat.vo.BuddyVO;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
import com.npc.common.modular.events.dto.EventsDto;
import com.npc.common.modular.holiday.vo.CalendarEventVO;
import com.npc.core.utils.StringUtils;
import com.npc.utils.DateUtils;
import com.npc.utils.DayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@Service
public class ChatBuddyServiceImpl extends ServiceImpl<ChatBuddyMapper, ChatBuddy> implements IChatBuddyService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyServiceImpl.class);

    @Resource
    private ChatBuddyMapper chatBuddyMapper;

    @Override
    public IPage<ChatBuddy> selectListByPage(BuddyVO buddyVO) {
        // 创建分页对象
        Page<ChatBuddy> page = new Page<>(buddyVO.getPageNum(), buddyVO.getPageSize());

        IPage<ChatBuddy> dailyScheduleIPage = this.baseMapper.getList(page, buddyVO);
        return dailyScheduleIPage;

//        QueryWrapper<ChatBuddy> queryWrapper = new QueryWrapper<>(); // 使用实体类作为泛型参数
//        // 假设你希望查询 hasContact 为 true 的记录（即 hasContact 不为 0）
//        queryWrapper.ne("has_contact", 0); // ne 表示 not equals
//
//        // 如果你想包含 hasContact 为 null 的情况（如果数据库允许该字段为 null）
//        queryWrapper.or().isNull("has_contact");
//
//        queryWrapper.orderByAsc("(case when sort is null then 1 else 0 end)");
//        queryWrapper.orderByAsc("sort");
//        // 执行分页查询，将查询结果封装到分页对象中
//        // 注意这里应该使用 BuddyEntity 的 Mapper 而不是 VO 的 Mapper，因为 VO 通常不直接与数据库表对应
//        IPage<ChatBuddy> buddyIPage = this.baseMapper.selectPage(page, queryWrapper);
//        return buddyIPage;
    }

    @Override
    public List<CalendarEventVO> getBirthdayList(EventsDto eventsDto) {
        // 处理农历日期数据为阳历
        LocalDate startDate = eventsDto.getStartDate();
        LocalDate endDate = eventsDto.getEndDate();
        // 将阳历日期转换为农历日期（如果您需要农历显示）
        String startLunarDate = DayUtils.gregorianToLunar(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String endLunarDate = DayUtils.gregorianToLunar(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        List<CalendarEventVO> lunchList = this.getLunchBirthday(eventsDto);
        // 提取查询年份
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.put("endDate", endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.put("queryYear", String.valueOf(startDate.getYear()));
        params.put("queryEndYear", String.valueOf(endDate.getYear()));
        params.put("queryLunarYear", startLunarDate.substring(0, 4));
        params.put("queryLunarEndYear", endLunarDate.substring(0, 4));
        params.put("startLunarDate", startLunarDate);
        params.put("endLunarDate", endLunarDate);
//        if (CollectionUtils.isEmpty(lunchList)) {
        List<CalendarEventVO> birthdayList = chatBuddyMapper.getBirthdayList(params);
        for (CalendarEventVO vo : birthdayList) {
            if (vo.getDateType().equals("lunar")) {
                vo.setDate(DayUtils.lunarToGregorian(vo.getDate(), Integer.parseInt(vo.getDate().substring(0, 4))));
            }
        }
        return birthdayList;
    }

    @Override
    public List<CalendarEventVO> getLunchBirthday(EventsDto eventsDto) {
        List<ChatBuddy> buddyList = chatBuddyMapper.selectList(
                new LambdaQueryWrapper<ChatBuddy>()
                        .isNull(ChatBuddy::getHasContact)
                        .or()
                        .ne(ChatBuddy::getHasContact, 0)
                        .isNotNull(ChatBuddy::getLunarBirthday)
                        .ne(ChatBuddy::getLunarBirthday, "")  // 排除空字符串
        );

        int targetYear = eventsDto.getStartDate().getYear();

        List<CalendarEventVO> buddyLunchBirthdayList = new ArrayList<>();

        for (ChatBuddy buddy : buddyList) {
            if (StringUtils.isBlank(buddy.getLunarBirthday())) {
                continue;
            }
            String solar = DayUtils.lunarToGregorian(buddy.getLunarBirthday(), targetYear);

            try {
                // 解析转换后的阳历日期
                LocalDate solarDate = LocalDate.parse(solar);

                // 判断调整后的日期是否在查询范围内
                if (!solarDate.isBefore(eventsDto.getStartDate()) &&
                        !solarDate.isAfter(eventsDto.getEndDate())) {
                    CalendarEventVO vo = new CalendarEventVO();
                    vo.setTitle(buddy.getName() + "农历生日");
                    vo.setDate(solarDate.toString());
                    vo.setDateType("lunar");
                    vo.setEventType("birthday");
                    buddyLunchBirthdayList.add(vo);
                }
            } catch (Exception e) {
                // 日期解析异常，跳过该条记录
                logger.warn("Failed to parse solar date: {}", solar, e);
            }
        }
        return buddyLunchBirthdayList;
    }
}
