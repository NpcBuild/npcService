package com.npc.common.modular.todoCompleted.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.todoCompleted.entity.TodoCompleted;
import com.npc.common.modular.todoCompleted.mapper.TodoCompletedMapper;
import com.npc.common.modular.todoCompleted.service.ITodoCompletedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务完成情况表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-01
 */
@Service
public class TodoCompletedServiceImpl extends ServiceImpl<TodoCompletedMapper, TodoCompleted> implements ITodoCompletedService {

    private static final Logger logger = LoggerFactory.getLogger(TodoCompletedServiceImpl.class);
}
