package com.npc.common.modular.diet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.diet.entity.Recipes;
import com.npc.common.modular.diet.mapper.RecipesMapper;
import com.npc.common.modular.diet.service.IRecipesService;
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

    /**
     * 按照菜名判断更新
     * @param recipeName 菜名
     * @return 更新结果
     */
    @Override
    public boolean saveIfNotExists(String recipeName) {
        // 构建查询条件，检查是否存在相同名称的菜谱
        QueryWrapper<Recipes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", recipeName);
        boolean exists = this.count(queryWrapper) > 0;

        if (!exists) {
            // 若不存在，则创建新的菜谱记录
            Recipes recipe = new Recipes();
            recipe.setName(recipeName);
            // 保存新记录
            return this.save(recipe);
        }
        return false;
    }
}
