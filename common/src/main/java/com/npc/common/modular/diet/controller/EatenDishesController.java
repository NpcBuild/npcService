package com.npc.common.modular.diet.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.diet.dto.EatenDishesDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.diet.service.IEatenDishesService;
import com.npc.common.modular.diet.entity.EatenDishes;

import java.util.Arrays;

/**
 * <p>
 * 饮食记录 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@RestController
@RequestMapping("/eatenDishes")
public class EatenDishesController {
    
    private static final Logger logger = LoggerFactory.getLogger(EatenDishesController.class);

    @Autowired
    public IEatenDishesService eatenDishesService;


    /**
     * 保存、修改 【区分id即可】
     * @param eatenDishes 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated EatenDishes eatenDishes) {
        try {
            Boolean obj = eatenDishesService.saveOrUpdate(eatenDishes);
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
    @GetMapping("deleteEatenDishesById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean eatenDishes =eatenDishesService.removeById(id);
            return ServerResponseVO.success(eatenDishes);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 饮食记录
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteEatenDishesByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteEatenDishesByIdList(@RequestParam("ids") Integer[] ids) {
        eatenDishesService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getEatenDishesById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        EatenDishes eatenDishes =eatenDishesService.getById(id);
        return ServerResponseVO.success(eatenDishes);
    }


    /**
     * 分页查询数据：
     * @param eatenDishesDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getEatenDishesList", method = RequestMethod.GET)
    public ServerResponseVO<?> getEatenDishesList(@Validated EatenDishesDto eatenDishesDto) {
        Page page = new Page(eatenDishesDto.getPageNum(), eatenDishesDto.getPageSize());
        QueryWrapper<EatenDishes> queryWrapper = new QueryWrapper(eatenDishesDto);
        Page<EatenDishes> pages = eatenDishesService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
