package com.npc.common.modular.reward.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.reward.dto.RewardClaimLogDto;
import com.npc.common.modular.reward.entity.RewardClaimLog;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.reward.service.IRewardClaimLogService;

import java.util.Arrays;

/**
 * <p>
 * 奖励兑换记录表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@RestController
@RequestMapping("/rewardClaimLog")
// @Api(value = "/rewardClaimLog", description = "奖励兑换记录表 相关接口")
public class RewardClaimLogController {
    
    private static final Logger logger = LoggerFactory.getLogger(RewardClaimLogController.class);

    @Autowired
    public IRewardClaimLogService rewardClaimLogService;


    /**
     * 保存、修改 【区分id即可】
     * @param rewardClaimLog 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "奖励兑换记录表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated RewardClaimLog rewardClaimLog) {
        try {
            Boolean obj = rewardClaimLogService.saveOrUpdate(rewardClaimLog);
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
    @GetMapping("deleteRewardClaimLogById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean rewardClaimLog =rewardClaimLogService.removeById(id);
            return ServerResponseVO.success(rewardClaimLog);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 奖励兑换记录表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteRewardClaimLogByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 奖励兑换记录表")
    public ServerResponseVO<?> batchDeleteRewardClaimLogByIdList(@RequestParam("ids") Integer[] ids) {
        rewardClaimLogService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getRewardClaimLogById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 奖励兑换记录表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        RewardClaimLog rewardClaimLog =rewardClaimLogService.getById(id);
        return ServerResponseVO.success(rewardClaimLog);
    }


    /**
     * 分页查询数据：
     * @param rewardClaimLogDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getRewardClaimLogList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "奖励兑换记录表 分页查询数据")
    public ServerResponseVO<?> getRewardClaimLogList(@Validated RewardClaimLogDto rewardClaimLogDto) {
        Page page = new Page(rewardClaimLogDto.getPageNum(), rewardClaimLogDto.getPageSize());
        QueryWrapper<RewardClaimLog> queryWrapper = new QueryWrapper(rewardClaimLogDto);
        Page<RewardClaimLog> pages = rewardClaimLogService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
