package com.npc.common.modular.plan.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.plan.dto.PlanDto;
import com.npc.common.modular.tags.entity.Tags;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.plan.service.IPlanService;
import com.npc.common.modular.plan.entity.Plan;

import java.util.Arrays;

/**
 * <p>
 * 曼陀罗计划表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2024-07-20
 */
@RestController
@RequestMapping("/plan")
public class PlanController {
    
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    public IPlanService planService;


    /**
     * 保存、修改 【区分id即可】
     * @param plan 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Plan plan) {
        try {
            Boolean obj = planService.saveOrUpdate(plan);
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
    @GetMapping("deletePlanById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean plan =planService.removeById(id);
            return ServerResponseVO.success(plan);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 曼陀罗计划表
     * @param ids
     * @return
     */
    @GetMapping("batchDeletePlanByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeletePlanByIdList(@RequestParam("ids") Integer[] ids) {
        planService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getPlanById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Plan plan =planService.getById(id);
        return ServerResponseVO.success(plan);
    }


    /**
     * 分页查询数据：
     * @param planDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getPlanList", method = RequestMethod.GET)
    public ServerResponseVO<?> getPlanList(@Validated PlanDto planDto) {
        Page page = new Page(planDto.getPageNum(), planDto.getPageSize());
        QueryWrapper<Plan> queryWrapper = new QueryWrapper(planDto);
        Page<Plan> pages = planService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
