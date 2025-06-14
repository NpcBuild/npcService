package com.npc.common.modular.diet.recipes.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.diet.recipes.entity.Recipes;
import com.npc.common.modular.diet.recipes.mapper.RecipesMapper;
import com.npc.common.modular.diet.recipes.service.IRecipesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 菜谱 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Service
public class RecipesServiceImpl extends ServiceImpl<RecipesMapper, Recipes> implements IRecipesService {

    private static final Logger logger = LoggerFactory.getLogger(RecipesServiceImpl.class);
}
