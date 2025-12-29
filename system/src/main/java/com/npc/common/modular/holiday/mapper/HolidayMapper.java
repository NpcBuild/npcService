package com.npc.common.modular.holiday.mapper;

import com.npc.common.modular.holiday.entity.Holiday;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.holiday.vo.CalendarEventVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-02-16
 */
@Mapper
public interface HolidayMapper extends BaseMapper<Holiday> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Holiday> getHolidayListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<CalendarEventVO> getList(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
}
