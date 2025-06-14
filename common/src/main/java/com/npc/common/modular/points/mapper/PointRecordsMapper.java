package com.npc.common.modular.points.mapper;

import com.npc.common.modular.points.entity.PointRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 积分变动记录表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Mapper
public interface PointRecordsMapper extends BaseMapper<PointRecords> {
    
	/**
     * 通过 积分变动记录表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<PointRecords> getPointRecordsListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<PointRecords> getMyPointRecords(@Param("userId") Long userId);
}
