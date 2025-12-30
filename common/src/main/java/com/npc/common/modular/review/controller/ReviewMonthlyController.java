package com.npc.common.modular.review.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.review.dto.ReviewMonthlyDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.review.service.IReviewMonthlyService;
import com.npc.common.modular.review.entity.ReviewMonthly;

import java.util.Arrays;

/**
 * <p>
 * 每月复盘表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@RestController
@RequestMapping("/reviewMonthly")
// @Api(value = "/reviewMonthly", description = "每月复盘表 相关接口")
public class ReviewMonthlyController {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviewMonthlyController.class);

    @Autowired
    public IReviewMonthlyService reviewMonthlyService;


    /**
     * 保存、修改 【区分id即可】
     * @param reviewMonthly 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "每月复盘表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ReviewMonthly reviewMonthly) {
        try {
            if (reviewMonthly.getId() == null) {
                Boolean obj = reviewMonthlyService.saveOrUpdate(reviewMonthly);
            }  else {
                Boolean obj = reviewMonthlyService.updateById(reviewMonthly);
            }
            return ServerResponseVO.success(reviewMonthly);
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
    @GetMapping("deleteReviewMonthlyById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean reviewMonthly =reviewMonthlyService.removeById(id);
            return ServerResponseVO.success(reviewMonthly);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 每月复盘表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteReviewMonthlyByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 每月复盘表")
    public ServerResponseVO<?> batchDeleteReviewMonthlyByIdList(@RequestParam("ids") Integer[] ids) {
        reviewMonthlyService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getReviewMonthlyById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 每月复盘表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ReviewMonthly reviewMonthly =reviewMonthlyService.getById(id);
        return ServerResponseVO.success(reviewMonthly);
    }


    /**
     * 分页查询数据：
     * @param reviewMonthlyDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getReviewMonthlyList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "每月复盘表 分页查询数据")
    public ServerResponseVO<?> getReviewMonthlyList(@Validated ReviewMonthlyDto reviewMonthlyDto) {
        Page page = new Page(reviewMonthlyDto.getPageNum(), reviewMonthlyDto.getPageSize());
        QueryWrapper<ReviewMonthly> queryWrapper = new QueryWrapper(reviewMonthlyDto);
        Page<ReviewMonthly> pages = reviewMonthlyService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
