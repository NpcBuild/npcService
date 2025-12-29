package com.npc.common.modular.career.mapper;

import com.npc.common.modular.career.entity.SolutionProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 解决方案 / 创业项目表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@Mapper
public interface SolutionProjectMapper extends BaseMapper<SolutionProject> {
    
	/**
     * 通过 解决方案 / 创业项目表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<SolutionProject> getSolutionProjectListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
