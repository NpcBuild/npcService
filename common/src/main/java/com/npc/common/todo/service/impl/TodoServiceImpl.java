package com.npc.common.todo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.todoCompleted.entity.TodoCompleted;
import com.npc.common.modular.todoCompleted.service.ITodoCompletedService;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.mapper.TodoMapper;
import com.npc.common.todo.service.ITodoService;
import com.npc.common.todo.vo.TodoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 任务清单表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-09-10
 */
@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements ITodoService {

    @Resource
    private ITodoCompletedService todoCompletedService;
    @Override
    public IPage<Todo> getTodoList(TodoVO vo) {
        // 创建分页对象
        Page<Todo> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper(vo);
        if (ObjectUtil.isNotEmpty(vo.getStartTime())) {
            queryWrapper.ge("end_time", vo.getStartTime());
            vo.setStartTime(null);
        }
        if (ObjectUtil.isNotEmpty(vo.getEndTime())) {
            queryWrapper.le("start_time", vo.getEndTime());
            vo.setEndTime(null);
        }

        // 执行分页查询，将查询结果封装到分页对象中
        IPage<Todo> todoPage = this.baseMapper.selectPage(page, queryWrapper);
        return todoPage;
    }

    @Override
    public List<TodoVO> getMoreInfo(List<Todo> todos, String day) {
        List<TodoVO> res = new ArrayList<>();
        for (Todo todo : todos) {
            TodoVO todoVO = new TodoVO();
            BeanUtil.copyProperties(todo, todoVO);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("todo_id", todo.getId());
            queryWrapper.likeRight("finish_time", day);
            TodoCompleted completed = todoCompletedService.getOne(queryWrapper);
            todoVO.setDone(completed != null);
            res.add(todoVO);
        }

        return res;
    }
}
