package com.npc.common.modular.money.mapper;

import com.npc.common.modular.money.entity.MoneyBudget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.money.vo.MoneyBudgetVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 用户预算管理表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-10-27
 */
@Mapper
public interface MoneyBudgetMapper extends BaseMapper<MoneyBudget> {
    
	/**
     * 通过 用户预算管理表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<MoneyBudget> getMoneyBudgetListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<MoneyBudgetVO> getBudgetList(@Param("userId") Integer userId, @Param("date") String date);
}
