package com.npc.common.modular.money.mapper;

import com.npc.common.modular.money.entity.Money;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-12-25
 */
@Mapper
public interface MoneyMapper extends BaseMapper<Money> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Money> getMoneyListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
