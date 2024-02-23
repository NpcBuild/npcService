package com.npc.common.quartz;

import com.npc.common.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author wow
 * @createTime 2022/9/17 23:07
 * @descripttion Cron表达式定时任务
 */

public class SimpleQuartzCron {
    /**
     * 基于Cron表达式的定时任务
     */
    public void simpleCron() throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job-1","job-group").build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-1","trigger-group")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 21 ? * * *"))
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();
        scheduler.shutdown();
    }
}
