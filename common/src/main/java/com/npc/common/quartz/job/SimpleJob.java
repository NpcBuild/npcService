package com.npc.common.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author wow
 * @createTime 2022/9/17 21:57
 * @descripttion 样例--执行的任务
 */
@Component
public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        // 创建一个事件
        System.out.println(Thread.currentThread().getName() + "--" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }
}
