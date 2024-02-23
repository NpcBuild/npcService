package com.npc.common.quartz;

import com.npc.common.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author wow
 * @createTime 2022/9/17 22:04
 * @descripttion 定时任务
 */

public class SimpleQuartz {
    /**
     * 基于时间间隔的定时任务
     */
    public void simple() throws SchedulerException, InterruptedException {
        // 1.创建Scheduler调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2.创建JobDetail实例，并与SimpleJob类绑定
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job","group")
                .build();
        // 3.构建Trigger（触发器），定义执行频率和时长
        Trigger trigger = TriggerBuilder.newTrigger()
                //指定唯一标识
                .withIdentity("trigger","trigger-group")
                //立即执行
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        //每隔2小时执行一次
                        .withIntervalInHours(2)
                        //永久执行
                        .repeatForever()).build();
        // 4.将Job和Trigger交给Scheduler调度
        scheduler.scheduleJob(jobDetail,trigger);
        // 5.启动Scheduler
        scheduler.start();
//        // 休眠，决定调度器运行时间，这里设置30s
//        TimeUnit.SECONDS.sleep(30);
        // 关闭Scheduler
        scheduler.shutdown();
    }
}
