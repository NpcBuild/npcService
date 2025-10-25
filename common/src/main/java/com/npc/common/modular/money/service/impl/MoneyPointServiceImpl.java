package com.npc.common.modular.money.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.MoneyPointDto;


import com.npc.common.modular.money.entity.MoneyPoint;
import com.npc.common.modular.money.mapper.MoneyPointMapper;
import com.npc.common.modular.money.service.IMoneyPointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 金额记录点 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@Service
public class MoneyPointServiceImpl extends ServiceImpl<MoneyPointMapper, MoneyPoint> implements IMoneyPointService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyPointServiceImpl.class);
}
