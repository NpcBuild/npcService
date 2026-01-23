package com.npc.common.modular.assets.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.assets.dto.AssetsFoodDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.assets.service.IAssetsFoodService;
import com.npc.common.modular.assets.entity.AssetsFood;

import java.util.Arrays;

/**
 * <p>
 * 食材资产扩展表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@RestController
@RequestMapping("/assetsFood")
// @Api(value = "/assetsFood", description = "食材资产扩展表 相关接口")
public class AssetsFoodController {
    
    private static final Logger logger = LoggerFactory.getLogger(AssetsFoodController.class);

    @Autowired
    public IAssetsFoodService assetsFoodService;


    /**
     * 保存、修改 【区分id即可】
     * @param assetsFood 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "食材资产扩展表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated AssetsFood assetsFood) {
        try {
            Boolean obj = assetsFoodService.saveOrUpdate(assetsFood);
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
    @GetMapping("deleteAssetsFoodById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean assetsFood =assetsFoodService.removeById(id);
            return ServerResponseVO.success(assetsFood);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 食材资产扩展表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteAssetsFoodByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 食材资产扩展表")
    public ServerResponseVO<?> batchDeleteAssetsFoodByIdList(@RequestParam("ids") Integer[] ids) {
        assetsFoodService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getAssetsFoodById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 食材资产扩展表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        AssetsFood assetsFood =assetsFoodService.getById(id);
        return ServerResponseVO.success(assetsFood);
    }


    /**
     * 分页查询数据：
     * @param assetsFoodDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getAssetsFoodList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "食材资产扩展表 分页查询数据")
    public ServerResponseVO<?> getAssetsFoodList(@Validated AssetsFoodDto assetsFoodDto) {
        Page page = new Page(assetsFoodDto.getPageNum(), assetsFoodDto.getPageSize());
        QueryWrapper<AssetsFood> queryWrapper = new QueryWrapper(assetsFoodDto);
        Page<AssetsFood> pages = assetsFoodService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
