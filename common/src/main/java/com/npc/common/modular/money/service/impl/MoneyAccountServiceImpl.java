package com.npc.common.modular.money.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.MoneyAccountDto;


import com.npc.common.modular.money.entity.MoneyAccount;
import com.npc.common.modular.money.mapper.MoneyAccountMapper;
import com.npc.common.modular.money.service.IMoneyAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 金额账户 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@Service
public class MoneyAccountServiceImpl extends ServiceImpl<MoneyAccountMapper, MoneyAccount> implements IMoneyAccountService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyAccountServiceImpl.class);
}
