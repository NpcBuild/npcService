package com.npc.common.quartz.config;

import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.service.impl.QuartzJobServiceImpl;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.common.quartz.utils.EnumJobEnable;
import com.npc.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author NPC
 * @description Spring Boot 容器启动时，加载启动所有任务
 * @create 2023/9/6 21:56
 */
@Slf4j
@Component
public class QuartzManager {

    @Resource
    private Scheduler scheduler;
    @Resource
    private SpringJobFactory springJobFactory;
    @Resource
    private QuartzJobServiceImpl quartzJobService;

    @PostConstruct
    public void start() {
        //启动所有任务
        try {
            scheduler.setJobFactory(springJobFactory);
            // scheduler.clear();
            List<QuartzJob> tasks = quartzJobService.selectTasks();
            for (QuartzJob taskInfo : tasks) {
                if (EnumJobEnable.START.getCode().equals(taskInfo.getStatus()) && !StringUtils.isEmpty(taskInfo.getCron())) {
                    QuartzJobVO data=new QuartzJobVO();
                    BeanUtils.copyProperties(taskInfo,data);
                    quartzJobService.addJob(data);
                }
            }
            log.info("定时任务启动完成");
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("定时任务初始化失败");
        }
    }
}
