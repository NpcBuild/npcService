package com.npc.common.quartz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.service.IQuartzJobService;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.common.quartz.job.SimpleJob;
import com.npc.core.ServerResponseVO;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author wow
 * @createTime 2022/10/21 22:48
 * @descripttion 动态定时任务
 */
@RestController
@RequestMapping("/quartz")
public class Quartz {

    @Resource
    private Scheduler scheduler;
    @Resource
    private IQuartzJobService quartzJobService;

    /**
     * 创建任务
     * @param jobName
     * @param jobGroup
     * @param cron
     * @param triggerName
     * @param triggerGroup
     * @throws SchedulerException
     */
    @GetMapping("/create")
    public void createJob(String jobName,String jobGroup,String cron,String triggerName,String triggerGroup) throws SchedulerException {

        // 1.创建Scheduler调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobKey jobKey = new JobKey(jobName,jobGroup);
        // 如果存在这个任务，则删除
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }

        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity(jobKey)
                .build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName,triggerGroup)
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,trigger);
    }



    /**定时器列表*/
    @PostMapping("/list")
    public IPage<QuartzJob> list(@RequestBody QuartzJobVO reqVo) {
        return quartzJobService.selectTaskListByPage(reqVo);
    }

    /**定时器修改*/
    @PostMapping("/edit")
    public ServerResponseVO edit(@RequestBody QuartzJobVO reqVo) {
        return quartzJobService.updateJob(reqVo);
    }

    /**暂停任务*/
    @PostMapping("/pause")
    public ServerResponseVO pause(Integer taskId) {
        return quartzJobService.pauseJob(taskId);
    }

    /**增加任务*/
    @PostMapping("/add")
    public ServerResponseVO add(@RequestBody QuartzJobVO taskInfoReq) {
        return quartzJobService.addJob(taskInfoReq);
    }

    /**恢复任务*/
    @PostMapping("/resume")
    public ServerResponseVO resume(Integer taskId) {
        return quartzJobService.resumeJob(taskId);
    }

    /**删除任务*/
    @PostMapping("/del")
    public ServerResponseVO delete(@RequestBody QuartzJobVO reqVo) {
        return quartzJobService.delete(reqVo);
    }
}
