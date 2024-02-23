package com.npc.common.modular.quartzJob.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.service.IQuartzJobService;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * <p>
 * 定时任务表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-09-06
 */
@Slf4j
@RestController
@RequestMapping("/quartzJob")
//@Api(value = "/quartzJob", description = "定时任务表 相关接口")
public class QuartzJobController {

    @Autowired
    public IQuartzJobService quartzJobService;


    /**
     * 保存、修改 【区分id即可】
     * @param quartzJob 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @ApiOperation(response = ServerResponseVO.class, value = "定时任务表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated QuartzJob quartzJob) {
        try {
            Boolean res = quartzJobService.save(quartzJob);
            return ServerResponseVO.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ServerResponseVO转换结果
     */
    @GetMapping("deleteQuartzJobById")
//    @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean res =quartzJobService.removeById(id);
            return ServerResponseVO.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 定时任务表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteQuartzJobByIdList")
    @Transactional(rollbackFor = Exception.class)
//    @ApiOperation(response = ServerResponseVO.class, value = "批量删除 定时任务表")
    public ServerResponseVO<?> batchDeleteQuartzJobByIdList(@RequestParam("ids") Integer[] ids) {

            quartzJobService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success("批量删除成功");
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getQuartzJobById", method = RequestMethod.GET)
//    @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 定时任务表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        return quartzJobService.pauseJob(id.intValue());
    }


    /**
     * 分页查询数据：
     * @param quartzJobVO 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getQuartzJobList", method = RequestMethod.GET)
//    @ApiOperation(response = ServerResponseVO.class, value = "定时任务表 分页查询数据")
    public ServerResponseVO<?> getQuartzJobList(@Validated QuartzJobVO quartzJobVO) {
        IPage<QuartzJob> page = quartzJobService.selectTaskListByPage(quartzJobVO);
        return ServerResponseVO.success(page);
    }
}
