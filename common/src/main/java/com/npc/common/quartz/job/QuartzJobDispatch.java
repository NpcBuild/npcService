package com.npc.common.quartz.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.mail.server.MailService;
import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.npc.common.modular.dailySchedule.mapper.DailyScheduleMapper;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.mapper.TodoMapper;
import com.npc.common.todo.service.ITodoService;
import com.npc.common.todo.vo.TodoVO;
import com.npc.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NPC
 * @description 定义一个调度器要执行的任务
 * @create 2023/9/7 12:42
 */
@Slf4j
@Component
public class QuartzJobDispatch extends QuartzJobBean {
    @Resource
    private MailService mailService;
    @Resource
    private ITodoService todoService;
    @Resource
    private TodoMapper todoMapper;
    @Resource
    private DailyScheduleMapper scheduleMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("幼年是盼盼，青年是晶晶，中年是冰墩墩，生活见好逐渐发福");
        // 发送消息提醒做任务
        String to = "1623285565@qq.com";
        String subject = "todo清单";
        String templateName = "todoV";

        // 每日安排
//        Todo todo = new Todo();
//        todo.setType("1");
//        QueryWrapper queryWrapper = new QueryWrapper(todo);
//        List<Todo> todoList = todoMapper.selectList(queryWrapper);
        DailySchedule schedule = new DailySchedule();
        QueryWrapper queryWrapper = new QueryWrapper(schedule);
        List<DailySchedule> scheduleList = scheduleMapper.selectList(queryWrapper);

        // 长期习惯
        Todo habit = new Todo();
        habit.setType("2");
        QueryWrapper queryWrapper2 = new QueryWrapper(habit);
        List<Todo> habits = todoMapper.selectList(queryWrapper2);
        List<TodoVO> habitList = todoService.getMoreInfo(habits, DateUtils.getDate());
        Map<String, Object> variables = new HashMap<>();
        variables.put("scheduleList",scheduleList);
        variables.put("habitList",habitList);
        variables.put("nowTime",DateUtils.getDate());
        variables.put("pageTitle","好好生活");
//        variables.put("taskList",todoList);
        try {
            mailService.sendEmail(to,subject,templateName,variables);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}