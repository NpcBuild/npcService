package com.npc.common.modular.review.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.review.dto.ReviewWeeklyDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.review.service.IReviewWeeklyService;
import com.npc.common.modular.review.entity.ReviewWeekly;

import java.util.Arrays;

/**
 * <p>
 * 每周复盘表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@RestController
@RequestMapping("/reviewWeekly")
// @Api(value = "/reviewWeekly", description = "每周复盘表 相关接口")
public class ReviewWeeklyController {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviewWeeklyController.class);

    @Autowired
    public IReviewWeeklyService reviewWeeklyService;


    /**
     * 保存、修改 【区分id即可】
     * @param reviewWeekly 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "每周复盘表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ReviewWeekly reviewWeekly) {
        try {
            if (reviewWeekly.getId() == null) {
                Boolean obj = reviewWeeklyService.saveOrUpdate(reviewWeekly);
            } else {
                Boolean obj = reviewWeeklyService.updateById(reviewWeekly);
            }
            return ServerResponseVO.success(reviewWeekly);
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
    @GetMapping("deleteReviewWeeklyById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean reviewWeekly =reviewWeeklyService.removeById(id);
            return ServerResponseVO.success(reviewWeekly);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 每周复盘表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteReviewWeeklyByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 每周复盘表")
    public ServerResponseVO<?> batchDeleteReviewWeeklyByIdList(@RequestParam("ids") Integer[] ids) {
        reviewWeeklyService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getReviewWeeklyById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 每周复盘表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ReviewWeekly reviewWeekly =reviewWeeklyService.getById(id);
        return ServerResponseVO.success(reviewWeekly);
    }


    /**
     * 分页查询数据：
     * @param reviewWeeklyDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getReviewWeeklyList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "每周复盘表 分页查询数据")
    public ServerResponseVO<?> getReviewWeeklyList(@Validated ReviewWeeklyDto reviewWeeklyDto) {
        Page page = new Page(reviewWeeklyDto.getPageNum(), reviewWeeklyDto.getPageSize());
        QueryWrapper<ReviewWeekly> queryWrapper = new QueryWrapper(reviewWeeklyDto);
        Page<ReviewWeekly> pages = reviewWeeklyService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
