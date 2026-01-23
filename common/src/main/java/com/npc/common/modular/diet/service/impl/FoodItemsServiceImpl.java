package com.npc.common.modular.diet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.FoodItemsDto;


import com.npc.common.modular.diet.entity.FoodItems;
import com.npc.common.modular.diet.mapper.FoodItemsMapper;
import com.npc.common.modular.diet.service.IFoodItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 每餐食物条目表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Service
public class FoodItemsServiceImpl extends ServiceImpl<FoodItemsMapper, FoodItems> implements IFoodItemsService {

    private static final Logger logger = LoggerFactory.getLogger(FoodItemsServiceImpl.class);
}
