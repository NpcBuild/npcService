package com.npc.common.modular.subscribe.mapper;

import com.npc.common.modular.subscribe.entity.Subscribe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 订阅表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-02-24
 */
@Mapper
public interface SubscribeMapper extends BaseMapper<Subscribe> {
    
	/**
     * 通过 订阅表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Subscribe> getSubscribeListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<Subscribe> getEnableList(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
