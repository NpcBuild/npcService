package com.npc.common.modular.review.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.review.dto.ReviewWeeklyTaskDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.review.service.IReviewWeeklyTaskService;
import com.npc.common.modular.review.entity.ReviewWeeklyTask;

import java.util.Arrays;

/**
 * <p>
 * 每周复盘-计划任务表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@RestController
@RequestMapping("/reviewWeeklyTask")
// @Api(value = "/reviewWeeklyTask", description = "每周复盘-计划任务表 相关接口")
public class ReviewWeeklyTaskController {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviewWeeklyTaskController.class);

    @Autowired
    public IReviewWeeklyTaskService reviewWeeklyTaskService;


    /**
     * 保存、修改 【区分id即可】
     * @param reviewWeeklyTask 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "每周复盘-计划任务表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ReviewWeeklyTask reviewWeeklyTask) {
        try {
            Boolean obj = reviewWeeklyTaskService.saveOrUpdate(reviewWeeklyTask);
            return ServerResponseVO.success(obj);
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
    @GetMapping("deleteReviewWeeklyTaskById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean reviewWeeklyTask =reviewWeeklyTaskService.removeById(id);
            return ServerResponseVO.success(reviewWeeklyTask);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 每周复盘-计划任务表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteReviewWeeklyTaskByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 每周复盘-计划任务表")
    public ServerResponseVO<?> batchDeleteReviewWeeklyTaskByIdList(@RequestParam("ids") Integer[] ids) {
        reviewWeeklyTaskService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getReviewWeeklyTaskById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 每周复盘-计划任务表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ReviewWeeklyTask reviewWeeklyTask =reviewWeeklyTaskService.getById(id);
        return ServerResponseVO.success(reviewWeeklyTask);
    }


    /**
     * 分页查询数据：
     * @param reviewWeeklyTaskDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getReviewWeeklyTaskList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "每周复盘-计划任务表 分页查询数据")
    public ServerResponseVO<?> getReviewWeeklyTaskList(@Validated ReviewWeeklyTaskDto reviewWeeklyTaskDto) {
        Page page = new Page(reviewWeeklyTaskDto.getPageNum(), reviewWeeklyTaskDto.getPageSize());
        QueryWrapper<ReviewWeeklyTask> queryWrapper = new QueryWrapper(reviewWeeklyTaskDto);
        Page<ReviewWeeklyTask> pages = reviewWeeklyTaskService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
