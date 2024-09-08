package com.npc.common.modular.problem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.problem.dto.ProblemDto;
import com.npc.common.modular.problem.entity.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.problem.vo.ProblemVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 问题及解决方案 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-12-21
 */
@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
    
	/**
     * 通过 问题及解决方案 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Problem> getProblemListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<Problem> search(ProblemDto problem);

    IPage<Problem> getList(@Param("vo") ProblemVO problemVO, Page<Problem> page);
}
