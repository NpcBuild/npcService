package com.npc.common.modular.assets.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.assets.dto.AssetsDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.assets.service.IAssetsService;
import com.npc.common.modular.assets.entity.Assets;

import java.util.Arrays;

/**
 * <p>
 * 物品表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@RestController
@RequestMapping("/assets")
public class AssetsController {
    
    private static final Logger logger = LoggerFactory.getLogger(AssetsController.class);

    @Autowired
    public IAssetsService assetsService;


    /**
     * 保存、修改 【区分id即可】
     * @param assets 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Assets assets) {
        try {
            Boolean obj = assetsService.saveOrUpdate(assets);
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
    @GetMapping("deleteAssetsById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean assets =assetsService.removeById(id);
            return ServerResponseVO.success(assets);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 物品表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteAssetsByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteAssetsByIdList(@RequestParam("ids") Integer[] ids) {
        assetsService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getAssetsById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Assets assets =assetsService.getById(id);
        return ServerResponseVO.success(assets);
    }


    /**
     * 分页查询数据：
     * @param assetsDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getAssetsList", method = RequestMethod.GET)
    public ServerResponseVO<?> getAssetsList(@Validated AssetsDto assetsDto) {
        Page page = new Page(assetsDto.getPageNum(), assetsDto.getPageSize());
        QueryWrapper<Assets> queryWrapper = new QueryWrapper(assetsDto);
        Page<Assets> pages = assetsService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
