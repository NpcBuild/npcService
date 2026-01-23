package com.npc.common.modular.diet.mapper;

import com.npc.common.modular.diet.entity.Recipes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 菜谱 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Mapper
public interface RecipesMapper extends BaseMapper<Recipes> {
    
	/**
     * 通过 菜谱 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Recipes> getRecipesListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
