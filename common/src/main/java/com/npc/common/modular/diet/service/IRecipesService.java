package com.npc.common.modular.diet.service;

import com.npc.common.modular.diet.entity.Recipes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜谱 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
public interface IRecipesService extends IService<Recipes> {
    // 按照菜名判断更新
    boolean saveIfNotExists(String recipeName);

}
