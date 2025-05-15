package com.npc.common.modular.plan.mapper;

import com.npc.common.modular.plan.entity.Plan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 曼陀罗计划表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2024-07-20
 */
@Mapper
public interface PlanMapper extends BaseMapper<Plan> {
    
	/**
     * 通过 曼陀罗计划表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Plan> getPlanListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<Plan> getPlanRoot();
}
