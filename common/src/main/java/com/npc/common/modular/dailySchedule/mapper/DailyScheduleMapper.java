package com.npc.common.modular.dailySchedule.mapper;

import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 每日日程安排 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-12-18
 */
@Mapper
public interface DailyScheduleMapper extends BaseMapper<DailySchedule> {
    
	/**
     * 通过 每日日程安排 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<DailySchedule> getDailyScheduleListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
