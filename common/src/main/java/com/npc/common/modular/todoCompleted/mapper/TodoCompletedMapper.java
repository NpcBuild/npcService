package com.npc.common.modular.todoCompleted.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.todoCompleted.entity.TodoCompleted;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务完成情况表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-12-01
 */
@Mapper
public interface TodoCompletedMapper extends BaseMapper<TodoCompleted> {
    
	/**
     * 通过 任务完成情况表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<TodoCompleted> getTodoCompletedListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    /**
     * 获取指定日期的已完成任务
     * @param date
     * @return
     */
    List<TodoCompleted> getCompletedList(@Param("date") String date);

}
