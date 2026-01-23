package com.npc.common.modular.diet.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.diet.service.IFoodItemsService;
import com.npc.common.modular.diet.entity.FoodItems;

import java.util.Arrays;

/**
 * <p>
 * 每餐食物条目表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@RestController
@RequestMapping("/foodItems")
// @Api(value = "/foodItems", description = "每餐食物条目表 相关接口")
public class FoodItemsController {
    
    private static final Logger logger = LoggerFactory.getLogger(FoodItemsController.class);

    @Autowired
    public IFoodItemsService foodItemsService;


    /**
     * 保存、修改 【区分id即可】
     * @param foodItems 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "每餐食物条目表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated FoodItems foodItems) {
        try {
            Boolean obj = foodItemsService.saveOrUpdate(foodItems);
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
    @GetMapping("deleteFoodItemsById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean foodItems =foodItemsService.removeById(id);
            return ServerResponseVO.success(foodItems);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 每餐食物条目表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteFoodItemsByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 每餐食物条目表")
    public ServerResponseVO<?> batchDeleteFoodItemsByIdList(@RequestParam("ids") Integer[] ids) {
        foodItemsService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getFoodItemsById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 每餐食物条目表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        FoodItems foodItems =foodItemsService.getById(id);
        return ServerResponseVO.success(foodItems);
    }


    /**
     * 分页查询数据：
     * @param foodItemsDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getFoodItemsList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "每餐食物条目表 分页查询数据")
    public ServerResponseVO<?> getFoodItemsList(@Validated FoodItemsDto foodItemsDto) {
        Page page = new Page(foodItemsDto.getPageNum(), foodItemsDto.getPageSize());
        QueryWrapper<FoodItems> queryWrapper = new QueryWrapper(foodItemsDto);
        Page<FoodItems> pages = foodItemsService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
