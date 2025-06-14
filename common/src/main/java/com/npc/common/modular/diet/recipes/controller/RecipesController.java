package com.npc.common.modular.diet.recipes.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.diet.recipes.dto.RecipesDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.diet.recipes.service.IRecipesService;
import com.npc.common.modular.diet.recipes.entity.Recipes;

import java.util.Arrays;

/**
 * <p>
 * 菜谱 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@RestController
@RequestMapping("/recipes")
//@Api(value = "/recipes", description = "菜谱 相关接口")
public class RecipesController {
    
    private static final Logger logger = LoggerFactory.getLogger(RecipesController.class);

    @Autowired
    public IRecipesService recipesService;


    /**
     * 保存、修改 【区分id即可】
     * @param recipes 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @ApiOperation(response = ServerResponseVO.class, value = "菜谱 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated Recipes recipes) {
        try {
            Boolean obj = recipesService.saveOrUpdate(recipes);
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
    @GetMapping("deleteRecipesById")
//    @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean recipes =recipesService.removeById(id);
            return ServerResponseVO.success(recipes);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 菜谱
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteRecipesByIdList")
    @Transactional(rollbackFor = Exception.class)
//    @ApiOperation(response = ServerResponseVO.class, value = "批量删除 菜谱")
    public ServerResponseVO<?> batchDeleteRecipesByIdList(@RequestParam("ids") Integer[] ids) {
        recipesService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getRecipesById", method = RequestMethod.GET)
//    @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 菜谱 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Recipes recipes =recipesService.getById(id);
        return ServerResponseVO.success(recipes);
    }


    /**
     * 分页查询数据：
     * @param recipesDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getRecipesList", method = RequestMethod.GET)
//    @ApiOperation(response = ServerResponseVO.class, value = "菜谱 分页查询数据")
    public ServerResponseVO<?> getRecipesList(@Validated RecipesDto recipesDto) {
        Page page = new Page(recipesDto.getPageNum(), recipesDto.getPageSize());
        QueryWrapper<Recipes> queryWrapper = new QueryWrapper(recipesDto);
        Page<Recipes> pages = recipesService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
