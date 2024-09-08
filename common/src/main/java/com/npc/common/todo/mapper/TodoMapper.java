package com.npc.common.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.vo.TodoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 任务清单表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-09-10
 */
@Mapper
public interface TodoMapper extends BaseMapper<Todo> {
    
	/**
     * 通过 任务清单表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Todo> getTodoListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    IPage<Todo> getList(Page<Todo> page, @Param("vo") TodoVO vo);

}
