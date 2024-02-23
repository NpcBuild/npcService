package com.npc.common.modular.quartzJob.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.mapper.QuartzJobMapper;
import com.npc.common.modular.quartzJob.service.IQuartzJobService;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.common.quartz.TodoManager;
import com.npc.common.quartz.utils.EnumJobEnable;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 定时任务表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-09-06
 */
@Slf4j
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements IQuartzJobService {
    @Autowired
    private QuartzJobMapper quartzJobMapper;
    @Autowired
    private TodoManager todoManager;

    @Override
    public IPage<QuartzJob> selectTaskListByPage(QuartzJobVO quartzJobVO) {
        // 创建分页对象
        Page<QuartzJob> page = new Page<>(quartzJobVO.getPageNum(), quartzJobVO.getPageSize());

        // 执行分页查询，将查询结果封装到分页对象中
        IPage<QuartzJob> userPage = quartzJobMapper.selectPage(page, null);

        return userPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO updateJob(QuartzJobVO quartzJobVO) {
        if (!CronExpression.isValidExpression(quartzJobVO.getCron())) {
            log.error("更新任务失败，表达式有误：{}", quartzJobVO.getCron());
            return ServerResponseVO.error(ServerResponseEnum.TASK_CRON_ERROR);
        }
        QuartzJob isExistData = quartzJobMapper.getByJobName(quartzJobVO.getJobName());
        //当任务存在，则更改失败
        if ((!Objects.isNull(isExistData)) && (!isExistData.getId().equals(quartzJobVO.getId()))) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_CRON_DOUBLE);
        }
        QuartzJob data = quartzJobMapper.selectById(quartzJobVO.getId());
        if (data == null) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_NOT_EXITES);
        }

        BeanUtils.copyProperties(quartzJobVO, data);
        data.setUpdateTime(new Date());
        quartzJobMapper.updateById(data);

        if (!todoManager.updateJob(data)) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_EXCEPTION);
        }
        return ServerResponseVO.success();
    }

    @Override
    public ServerResponseVO pauseJob(Integer taskId) {
        QuartzJob data = quartzJobMapper.selectById(taskId);
        if (data == null) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_NOT_EXITES);
        }
        if (!todoManager.pauseJob(data)) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_EXCEPTION);
        }
        data.setStatus(EnumJobEnable.STOP.getCode());
        quartzJobMapper.updateById(data);
        return ServerResponseVO.success();
    }

    @Override
    public ServerResponseVO resumeJob(Integer taskId) {
        QuartzJob data = quartzJobMapper.selectById(taskId);
        if (data == null) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_NOT_EXITES);
        }
        if (!todoManager.resumeJob(data)) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_EXCEPTION);
        }
        data.setStatus(EnumJobEnable.START.getCode());
        quartzJobMapper.updateById(data);
        return ServerResponseVO.success();
    }

    @Override
    public ServerResponseVO addJob(QuartzJobVO quartzJobVO) {
        if (!todoManager.addJob(quartzJobVO)) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_EXCEPTION);
        }
        QuartzJob data = quartzJobMapper.getByJobName(quartzJobVO.getJobName());
        //当任务不存在，则返回成功插入
        if (Objects.isNull(data)) {
            data = new QuartzJob();
            BeanUtils.copyProperties(quartzJobVO, data);
            data.setStatus("2"); // 运行中
            data.setCreateTime(new Date());
            quartzJobMapper.insert(data);
            return ServerResponseVO.success(data.getId());
        } else {
            return ServerResponseVO.error(ServerResponseEnum.TASK_CRON_DOUBLE);
        }

    }

    @Override
    public ServerResponseVO delete(QuartzJobVO quartzJobVO) {
        try {
            //TODO 删除任务只是做了暂停，如果是 Quartz Jdbc 模式下添加重复任务可能加不进去，并没有真正删除(可自行调整)
            ServerResponseVO result = this.pauseJob(quartzJobVO.getId());
            //只有暂停成功的任务才能删除
            if (ServerResponseEnum.SUCCESS.getCode() == result.getCode()) {
                quartzJobMapper.deleteById(quartzJobVO.getId());
                return ServerResponseVO.success();
            } else {
                return ServerResponseVO.error(ServerResponseEnum.TASK_EXCEPTION);
            }
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.TASK_EXCEPTION);
        }
    }

    @Override
    public List<QuartzJob> selectTasks() {
        QueryWrapper queryWrapper = new QueryWrapper();
        return quartzJobMapper.selectList(queryWrapper);
    }
}
