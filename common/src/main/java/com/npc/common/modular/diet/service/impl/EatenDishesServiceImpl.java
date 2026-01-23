package com.npc.common.modular.diet.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.diet.entity.EatenDishes;
import com.npc.common.modular.diet.mapper.EatenDishesMapper;
import com.npc.common.modular.diet.service.IEatenDishesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 饮食记录 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Service
public class EatenDishesServiceImpl extends ServiceImpl<EatenDishesMapper, EatenDishes> implements IEatenDishesService {

    private static final Logger logger = LoggerFactory.getLogger(EatenDishesServiceImpl.class);
}
