package com.npc.common.modular.money.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.money.dto.MoneyDto;
import com.npc.common.modular.money.entity.Money;
import com.npc.common.modular.money.mapper.MoneyMapper;
import com.npc.common.modular.money.service.IMoneyService;
import com.npc.common.modular.money.vo.MoneyReport;
import com.npc.common.modular.money.vo.MoneyVO;
import com.npc.common.todo.entity.Todo;
import com.npc.core.utils.StringUtils;
import com.npc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-25
 */
@Service
public class MoneyServiceImpl extends ServiceImpl<MoneyMapper, Money> implements IMoneyService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyServiceImpl.class);

    @Override
    public MoneyReport getInfo(String date) {
        Money money = new Money();
        money.setDateStartS(DateUtils.getFirstDayOfMonth(LocalDate.parse(date)));
        money.setDateEndS(DateUtils.getFirstDayOfNextMonth(LocalDate.parse(date)));
        money.setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return this.baseMapper.getInfo(money);
    }

    @Override
    public IPage<Money> getListPage(MoneyDto dto) {
        // 创建分页对象
        Page<Money> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        // 执行分页查询，将查询结果封装到分页对象中
        IPage<Money> moneyPage = this.baseMapper.getList(page, dto);
//        IPage<Money> moneyPage = this.baseMapper.selectPage(page, queryWrapper);
        return moneyPage;
    }

    @Override
    public Object getChart(MoneyDto moneyDto) {
        if (StringUtils.isBlank(moneyDto.getDateStartS())) {
            moneyDto.setDateStartS(DateUtils.getFirstDayOfMonth(LocalDate.now()));
        }
        BigDecimal willPayMoney = this.baseMapper.getSubMoneyCount(null, moneyDto.getDateStartS(), moneyDto.getDateEndS());
        List<String> categories = this.baseMapper.getCategory();
        Map<String, Object> chart = this.baseMapper.getChart(categories);

        if ("time".equals(moneyDto.getResType())) {
            chart.put("willPayMoney", willPayMoney);
            return chart;
        }
        Map<String, Object> res = new HashMap<>();
        res.put("willPayMoney", String.valueOf(willPayMoney));
        List<Map<String, Object>> monthList = new ArrayList<>();

        for (Map.Entry<String, Object> monthInfo : chart.entrySet()) {
            String date = monthInfo.getKey();
            Map<String, Object> monthMap = new HashMap<>();
            monthMap.put("date", date);

            List<Map<String, Object>> moneyList = new ArrayList<>();
            Map<String, Object> entryMap = (Map<String, Object>) monthInfo.getValue();

            for (Map.Entry<String, Object> typeEntry : entryMap.entrySet()) {
                String type = typeEntry.getKey();
                // 跳过 "month" 字段
                if ("month".equals(type)) {
                    continue;
                }
                Map<String, Object> typeMap = new HashMap<>();
                typeMap.put("type", type);
                typeMap.put("money", typeEntry.getValue());
                moneyList.add(typeMap);
            }

            monthMap.put("money", moneyList);
            monthList.add(monthMap);
        }
        res.put("month", monthList);
        return res;
    }
}
