package com.npc.common.modular.money.service.impl;

import com.npc.common.modular.money.entity.MoneyBudget;
import com.npc.common.modular.money.mapper.MoneyBudgetMapper;
import com.npc.common.modular.money.service.IMoneyBudgetService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.MoneyBudgetDto;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户预算管理表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-10-27
 */
@Service
public class MoneyBudgetServiceImpl extends ServiceImpl<MoneyBudgetMapper, MoneyBudget> implements IMoneyBudgetService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyBudgetServiceImpl.class);
}
