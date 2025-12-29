package com.npc.common.modular.review.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.review.dto.ReviewDailyDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.review.service.IReviewDailyService;
import com.npc.common.modular.review.entity.ReviewDaily;

import java.util.Arrays;

/**
 * <p>
 * 每日复盘表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@RestController
@RequestMapping("/reviewDaily")
// @Api(value = "/reviewDaily", description = "每日复盘表 相关接口")
public class ReviewDailyController {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviewDailyController.class);

    @Autowired
    public IReviewDailyService reviewDailyService;


    /**
     * 保存、修改 【区分id即可】
     * @param reviewDaily 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "每日复盘表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ReviewDaily reviewDaily) {
        try {
            Boolean obj = reviewDailyService.saveOrUpdate(reviewDaily);
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
    @GetMapping("deleteReviewDailyById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean reviewDaily =reviewDailyService.removeById(id);
            return ServerResponseVO.success(reviewDaily);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 每日复盘表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteReviewDailyByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 每日复盘表")
    public ServerResponseVO<?> batchDeleteReviewDailyByIdList(@RequestParam("ids") Integer[] ids) {
        reviewDailyService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getReviewDailyById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 每日复盘表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ReviewDaily reviewDaily =reviewDailyService.getById(id);
        return ServerResponseVO.success(reviewDaily);
    }


    /**
     * 分页查询数据：
     * @param reviewDailyDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getReviewDailyList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "每日复盘表 分页查询数据")
    public ServerResponseVO<?> getReviewDailyList(@Validated ReviewDailyDto reviewDailyDto) {
        Page page = new Page(reviewDailyDto.getPageNum(), reviewDailyDto.getPageSize());
        QueryWrapper<ReviewDaily> queryWrapper = new QueryWrapper(reviewDailyDto);
        Page<ReviewDaily> pages = reviewDailyService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
