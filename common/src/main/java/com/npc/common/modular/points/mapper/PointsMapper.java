package com.npc.common.modular.points.mapper;

import com.npc.common.modular.points.entity.Points;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 用户积分余额表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Mapper
public interface PointsMapper extends BaseMapper<Points> {
    
	/**
     * 通过 用户积分余额表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Points> getPointsListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    Points getMyPoints(@Param("userId") Long userId);
}
