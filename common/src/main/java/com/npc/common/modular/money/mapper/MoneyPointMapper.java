package com.npc.common.modular.money.mapper;

import com.npc.common.modular.money.entity.MoneyPoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 金额记录点 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@Mapper
public interface MoneyPointMapper extends BaseMapper<MoneyPoint> {
    
	/**
     * 通过 金额记录点 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<MoneyPoint> getMoneyPointListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
