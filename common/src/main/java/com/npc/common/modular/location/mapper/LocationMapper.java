package com.npc.common.modular.location.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.location.dto.LocationDto;
import com.npc.common.modular.location.entity.Location;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2024-06-23
 */
@Mapper
public interface LocationMapper extends BaseMapper<Location> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Location> getLocationListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    Page<Location> findList(Page<Location> page, @Param("dto")  LocationDto locationDto);
}
