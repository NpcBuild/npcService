package com.npc.common.modular.diet.mapper;

import com.npc.common.modular.diet.entity.FoodItems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 每餐食物条目表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Mapper
public interface FoodItemsMapper extends BaseMapper<FoodItems> {
    
	/**
     * 通过 每餐食物条目表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<FoodItems> getFoodItemsListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
