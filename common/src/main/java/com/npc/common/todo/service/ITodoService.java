package com.npc.common.todo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.vo.TodoVO;

import java.util.List;

/**
 * <p>
 * 任务清单表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-09-10
 */
public interface ITodoService extends IService<Todo> {
    IPage<Todo> getTodoList(TodoVO vo);

    /**
     * 扩展更多信息
     * @param todo
     * @return
     */
    List<TodoVO> getMoreInfo(List<Todo> todos, String day);
}
