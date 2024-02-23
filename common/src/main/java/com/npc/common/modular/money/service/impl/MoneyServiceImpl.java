package com.npc.common.modular.money.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.money.entity.Money;
import com.npc.common.modular.money.mapper.MoneyMapper;
import com.npc.common.modular.money.service.IMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
