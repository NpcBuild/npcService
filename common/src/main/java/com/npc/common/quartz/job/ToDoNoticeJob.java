package com.npc.common.quartz.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.mapper.TodoMapper;
import com.npc.common.todo.service.ITodoService;
import com.npc.common.todo.vo.TodoVO;
import com.npc.common.utils.SpringUtils;
import com.npc.redis.utils.RedisUtil;
import com.npc.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2024/7/8 9:15
 */
@Slf4j
@Component
public class ToDoNoticeJob extends QuartzJobBean {
    public static final String UN_DONE = "task-un";
    @Resource
    private ITodoService todoService;
    @Resource
    private TodoMapper todoMapper;
    @Autowired
    RedisUtil redisUtil;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 长期习惯
        Todo habit = new Todo();
        habit.setType("2");
        QueryWrapper queryWrapper = new QueryWrapper(habit);
        List<Todo> habits = todoMapper.selectList(queryWrapper);
        List<TodoVO> habitList = todoService.getMoreInfo(habits, DateUtils.getDate());
        Integer unDone = 0;
        for (TodoVO todoVO : habitList) {
            if (!todoVO.getDone()) {
                unDone++;
            }
        }
        if (unDone > 0) {
            Integer unDoneNum = unDone;
            if (redisUtil.hasKey(UN_DONE)) {
                unDoneNum = Integer.valueOf(redisUtil.get(UN_DONE)) + unDone;
            }
            redisUtil.set(UN_DONE, unDoneNum + "");
            System.out.println("未完成习惯数为：" + unDone);
            System.out.println("总完成习惯数为：" + unDoneNum);
        }
        System.out.println("今日习惯已全部完成");
    }

    public static void main(String[] args) {
//        TodoMapper todoMapper = SpringUtils.getBean(TodoMapper.class);
//        ITodoService todoService = SpringUtils.getBean(ITodoService.class);
//        RedisUtil redisUtil = SpringUtils.getBean(RedisUtil.class);
//
//        Todo habit = new Todo();
//        habit.setType("2");
//        QueryWrapper queryWrapper = new QueryWrapper(habit);
//        List<Todo> habits = todoMapper.selectList(queryWrapper);
//        List<TodoVO> habitList = todoService.getMoreInfo(habits, DateUtils.getDate());
//        Integer unDone = 0;
//        for (TodoVO todoVO : habitList) {
//            if (!todoVO.getDone()) {
//                unDone++;
//            }
//        }
//        if (unDone > 0) {
//            Integer unDoneNum = unDone;
//            if (redisUtil.hasKey(UN_DONE)) {
//                unDoneNum = Integer.valueOf(redisUtil.get(UN_DONE)) + unDone;
//            }
//            redisUtil.set(UN_DONE, unDoneNum.toString(), 0);
//            System.out.println("未完成习惯数为：" + unDone);
//            System.out.println("总完成习惯数为：" + unDoneNum);
//        }
//        System.out.println("今日习惯已全部完成");
    }
}
