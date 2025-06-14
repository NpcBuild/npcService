package com.npc.common.todo.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import com.npc.common.todo.vo.TodoViewVO;
import com.npc.utils.DateUtils;
import com.npc.core.utils.StringUtils;
import org.springframework.stereotype.Service;

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
        String startDate = vo.getStartDate(),endDate = vo.getEndDate();
        if (StringUtils.isNotEmpty(vo.getDate())) {
            startDate = DateUtils.getDate();
            endDate = DateUtils.getDate();
        } else {
            if (StringUtils.isEmpty(startDate)) {
                startDate = DateUtils.getDate();
            }
            if (StringUtils.isEmpty(endDate)) {
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
    public IPage<TodoViewVO> getListPage(TodoVO vo) {
        // 创建分页对象
        Page<TodoViewVO> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        // 构建查询条件
        setQueryDate(vo);
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

    static void setQueryDate(TodoVO vo) {
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
        // 任务循环模式查询
        // recurrence_type 循环类型 ('none','daily','weekly','monthly','workDay','restDay','custom')
        // recurrence_days 自定义循环（如['Monday','Thursday']）
        // recurrence_interval 间隔天数（如每X天，每X周）
        // next_due_date 下次执行时间
        /*
          无循环 recurrence_type = none
          每天 recurrence_type = daily
          每周 recurrence_type = weekly
          每月 recurrence_type = monthly
          工作日 recurrence_type = workDay
          休息日 recurrence_type = restDay
          自定义 recurrence_type = custom recurrence_days = ['Monday','Thursday'] recurrence_interval = 2
          每周工作日
          每周六、周日 recurrence_type = weekly recurrence_days = ['Monday','Thursday']
          每月的第X周 recurrence_type = monthly recurrence_interval = 2
          每年的第X月 recurrence_type = yearly recurrence_interval = 2
          每周的星期？
          每月的？号
          艾宾浩斯记忆法
          定期循环（每？天）
          自定义频次（每周？天）
         */

    }
}
