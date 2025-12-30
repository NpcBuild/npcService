package com.npc.common.modular.review.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.review.dto.ReviewYearlyDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.review.service.IReviewYearlyService;
import com.npc.common.modular.review.entity.ReviewYearly;

import java.util.Arrays;

/**
 * <p>
 * 年度复盘表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@RestController
@RequestMapping("/reviewYearly")
// @Api(value = "/reviewYearly", description = "年度复盘表 相关接口")
public class ReviewYearlyController {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviewYearlyController.class);

    @Autowired
    public IReviewYearlyService reviewYearlyService;


    /**
     * 保存、修改 【区分id即可】
     * @param reviewYearly 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "年度复盘表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ReviewYearly reviewYearly) {
        try {
            if (reviewYearly.getId() == null) {
                Boolean obj = reviewYearlyService.saveOrUpdate(reviewYearly);
            } else {
                Boolean obj = reviewYearlyService.updateById(reviewYearly);
            }
            return ServerResponseVO.success(reviewYearly);
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
    @GetMapping("deleteReviewYearlyById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean reviewYearly =reviewYearlyService.removeById(id);
            return ServerResponseVO.success(reviewYearly);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 年度复盘表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteReviewYearlyByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 年度复盘表")
    public ServerResponseVO<?> batchDeleteReviewYearlyByIdList(@RequestParam("ids") Integer[] ids) {
        reviewYearlyService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getReviewYearlyById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 年度复盘表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ReviewYearly reviewYearly =reviewYearlyService.getById(id);
        return ServerResponseVO.success(reviewYearly);
    }


    /**
     * 分页查询数据：
     * @param reviewYearlyDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getReviewYearlyList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "年度复盘表 分页查询数据")
    public ServerResponseVO<?> getReviewYearlyList(@Validated ReviewYearlyDto reviewYearlyDto) {
        Page page = new Page(reviewYearlyDto.getPageNum(), reviewYearlyDto.getPageSize());
        QueryWrapper<ReviewYearly> queryWrapper = new QueryWrapper(reviewYearlyDto);
        Page<ReviewYearly> pages = reviewYearlyService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
