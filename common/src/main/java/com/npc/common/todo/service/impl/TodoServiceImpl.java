package com.npc.common.todo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mchange.lang.IntegerUtils;
import com.npc.common.modular.todoCompleted.entity.TodoCompleted;
import com.npc.common.modular.todoCompleted.service.ITodoCompletedService;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.mapper.TodoMapper;
import com.npc.common.todo.service.ITodoService;
import com.npc.common.todo.vo.TodoVO;
import com.npc.utils.DateUtils;
import com.npc.core.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
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
    public List<Todo> getList(TodoVO vo) {
        String startDate = null,endDate = null;
        if (StringUtils.isNotEmpty(vo.getDate())) {
            startDate = DateUtils.getDate();
            endDate = DateUtils.getDate();
        } else {
            if (StringUtils.isEmpty(vo.getStartDate())) {
                startDate = DateUtils.getDate();
            }
            if (StringUtils.isEmpty(vo.getEndDate())) {
                endDate = DateUtils.getNextDay(LocalDate.parse(startDate)).toString();
            }
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(endDate)) {
            queryWrapper.ge("end_time", endDate);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            queryWrapper.le("start_time", startDate);
        }
        if (StringUtils.isNotEmpty(vo.getType())) {
            queryWrapper.eq("type", vo.getType());
        }
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<Todo> getListPage(TodoVO vo) {
        // 创建分页对象
        Page<Todo> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        if (StringUtils.isNotEmpty(vo.getDate())) {
            vo.setStartDate(vo.getDate());
            vo.setEndDate(DateUtils.getNextDay(LocalDate.parse(vo.getDate())).toString());
        } else {
            if (StringUtils.isEmpty(vo.getStartDate())) {
                vo.setStartDate(DateUtils.getDate());
            }
            if (StringUtils.isEmpty(vo.getEndDate())) {
                vo.setEndDate(DateUtils.getNextDay(LocalDate.parse(vo.getStartDate())).toString());
            }
        }
        return this.baseMapper.getList(page, vo);
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
