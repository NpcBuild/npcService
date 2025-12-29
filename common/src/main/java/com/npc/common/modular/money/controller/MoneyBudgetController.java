package com.npc.common.modular.money.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.money.dto.MoneyBudgetDto;
import com.npc.common.modular.money.entity.MoneyBudget;
import com.npc.common.modular.money.mapper.MoneyBudgetMapper;
import com.npc.common.modular.money.service.IMoneyBudgetService;
import com.npc.common.modular.money.vo.MoneyBudgetVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户预算管理表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-10-27
 */
@RestController
@RequestMapping("/moneyBudget")
// @Api(value = "/moneyBudget", description = "用户预算管理表 相关接口")
public class MoneyBudgetController {
    
    private static final Logger logger = LoggerFactory.getLogger(MoneyBudgetController.class);

    @Autowired
    public IMoneyBudgetService moneyBudgetService;
    @Resource
    private MoneyBudgetMapper moneyBudgetMapper;


    /**
     * 保存、修改 【区分id即可】
     * @param moneyBudget 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "用户预算管理表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated MoneyBudget moneyBudget) {
        try {
            Boolean obj = moneyBudgetService.saveOrUpdate(moneyBudget);
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
    @GetMapping("deleteMoneyBudgetById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean moneyBudget =moneyBudgetService.removeById(id);
            return ServerResponseVO.success(moneyBudget);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 用户预算管理表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteMoneyBudgetByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 用户预算管理表")
    public ServerResponseVO<?> batchDeleteMoneyBudgetByIdList(@RequestParam("ids") Integer[] ids) {
        moneyBudgetService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getMoneyBudgetById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 用户预算管理表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        MoneyBudget moneyBudget =moneyBudgetService.getById(id);
        return ServerResponseVO.success(moneyBudget);
    }


    /**
     * 分页查询数据：
     * @param moneyBudgetDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getMoneyBudgetList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "用户预算管理表 分页查询数据")
    public ServerResponseVO<?> getMoneyBudgetList(@Validated MoneyBudgetDto moneyBudgetDto) {
        Page page = new Page(moneyBudgetDto.getPageNum(), moneyBudgetDto.getPageSize());
        QueryWrapper<MoneyBudget> queryWrapper = new QueryWrapper(moneyBudgetDto);
        Page<MoneyBudget> pages = moneyBudgetService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }

    /**
     * 分页查询数据：
     * @param
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getBudgetList", method = RequestMethod.GET)
    public ServerResponseVO<?> getBudgetList() {
        List<MoneyBudgetVO> budgetList = moneyBudgetMapper.getBudgetList(1, DateUtils.getDate());
        return ServerResponseVO.success(budgetList);
    }
}
