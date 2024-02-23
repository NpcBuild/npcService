package com.npc.common.modular.quartzJob.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.core.ServerResponseVO;

import java.util.List;

/**
 * <p>
 * 定时任务表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-09-06
 */
public interface IQuartzJobService extends IService<QuartzJob> {

    /**获取任务列表分页*/
    IPage<QuartzJob> selectTaskListByPage(QuartzJobVO quartzJobVO);
    /**添加定时任务*/
    ServerResponseVO addJob(QuartzJobVO quartzJobVO);
    /**更新任务*/
    ServerResponseVO updateJob(QuartzJobVO quartzJobVO);
    /**暂停任务*/
    ServerResponseVO pauseJob(Integer taskId);
    /**恢复任务*/
    ServerResponseVO resumeJob(Integer taskId);
    /**删除任务*/
    ServerResponseVO delete(QuartzJobVO quartzJobVO);
    /**获取所有任务*/
    List<QuartzJob> selectTasks();
}
