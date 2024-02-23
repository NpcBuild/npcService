package com.npc.common.quartz;

import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author NPC
 * @description 任务管理器，主要接收业务指令，来完成对 Quartz 容器进行操作
 * 1、添加任务 2、更新任务 3、暂停任务 4、恢复任务
 * @create 2023/9/6 21:50
 */
@Slf4j
@Component
public class TodoManager {
    public static final String JOB_DEFAULT_GROUP_NAME = "JOB_DEFAULT_GROUP_NAME";
    public static final String TRIGGER_DEFAULT_GROUP_NAME = "TRIGGER_DEFAULT_GROUP_NAME";
    @Resource
    private Scheduler scheduler;
    @Resource
    private SpringUtils springUtils;

    /**
     * 添加任务
     */
    public boolean addJob(QuartzJobVO quartzJobVO) {
        boolean flag = true;
        if (!CronExpression.isValidExpression(quartzJobVO.getCron())) {
            log.error("定时任务表达式有误：{}", quartzJobVO.getCron());
            return false;
        }
        try {
            String className = springUtils.getBean(Class.forName(quartzJobVO.getJobName())).getClass().getName();
            JobDetail jobDetail = JobBuilder.newJob().withIdentity(new JobKey(quartzJobVO.getJobName(), JOB_DEFAULT_GROUP_NAME))
                    .ofType((Class<Job>) Class.forName(className))
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJobVO.getCron()))
                    .withIdentity(new TriggerKey(quartzJobVO.getJobName(), TRIGGER_DEFAULT_GROUP_NAME))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            log.error("添加定时任务异常：{}", e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 更新任务
     */
    public boolean updateJob(QuartzJob quartzJob) {
        boolean flag = true;
        try {
            JobKey jobKey = new JobKey(quartzJob.getJobName(), JOB_DEFAULT_GROUP_NAME);
            TriggerKey triggerKey = new TriggerKey(quartzJob.getJobName(), TRIGGER_DEFAULT_GROUP_NAME);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
                Trigger newTrigger = TriggerBuilder.newTrigger()
                        .forJob(jobDetail)
                        .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCron()))
                        .withIdentity(triggerKey)
                        .build();
                scheduler.rescheduleJob(triggerKey, newTrigger);
            } else {
                log.info("更新任务失败，任务不存在，任务名称：{}，表达式：{}", quartzJob.getJobName(), quartzJob.getCron());
            }
            log.info("更新任务成功，任务名称：{}，表达式：{}", quartzJob.getJobName(), quartzJob.getCron());
        } catch (SchedulerException e) {
            log.error("更新定时任务失败:{}", e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 暂停任务
     */
    public boolean pauseJob(QuartzJob quartzJob) {
        try {
            scheduler.pauseJob(JobKey.jobKey(quartzJob.getJobName(), JOB_DEFAULT_GROUP_NAME));
            log.info("任务暂停成功：{}", quartzJob.getId());
            return true;
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 恢复任务
     */
    public boolean resumeJob(QuartzJob quartzJob) {
        try {
            scheduler.resumeJob(JobKey.jobKey(quartzJob.getJobName(), JOB_DEFAULT_GROUP_NAME));
            log.info("任务恢复成功：{}", quartzJob.getId());
            return true;
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败:{}", e.getMessage(), e);
            return false;
        }
    }
}
