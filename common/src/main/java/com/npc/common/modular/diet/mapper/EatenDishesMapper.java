package com.npc.common.modular.diet.mapper;

import com.npc.common.modular.diet.entity.EatenDishes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 饮食记录 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Mapper
public interface EatenDishesMapper extends BaseMapper<EatenDishes> {
    
	/**
     * 通过 饮食记录 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<EatenDishes> getEatenDishesListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
