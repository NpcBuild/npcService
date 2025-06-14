package com.npc.common.modular.reward.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.reward.dto.RewardDto;
import com.npc.common.modular.reward.entity.Reward;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.reward.service.IRewardService;

import java.util.Arrays;

/**
 * <p>
 * 奖励定义表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@RestController
@RequestMapping("/reward")
// @Api(value = "/reward", description = "奖励定义表 相关接口")
public class RewardController {
    
    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    @Autowired
    public IRewardService rewardService;


    /**
     * 保存、修改 【区分id即可】
     * @param reward 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "奖励定义表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated Reward reward) {
        try {
            Boolean obj = rewardService.saveOrUpdate(reward);
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
    @GetMapping("deleteRewardById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean reward =rewardService.removeById(id);
            return ServerResponseVO.success(reward);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 奖励定义表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteRewardByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 奖励定义表")
    public ServerResponseVO<?> batchDeleteRewardByIdList(@RequestParam("ids") Integer[] ids) {
        rewardService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getRewardById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 奖励定义表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Reward reward =rewardService.getById(id);
        return ServerResponseVO.success(reward);
    }


    /**
     * 分页查询数据：
     * @param rewardDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getRewardList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "奖励定义表 分页查询数据")
    public ServerResponseVO<?> getRewardList(@Validated RewardDto rewardDto) {
        Page page = new Page(rewardDto.getPageNum(), rewardDto.getPageSize());
        QueryWrapper<Reward> queryWrapper = new QueryWrapper(rewardDto);
        Page<Reward> pages = rewardService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }

    /**
     * 奖励兑换
     */
    @PostMapping("exchange")
    public ServerResponseVO<?> exchange(@RequestBody Reward reward) {
        return ServerResponseVO.success(rewardService.exchange(reward));
    }
}
