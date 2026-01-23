package com.npc.common.modular.assets.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.assets.dto.AssetLocationDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.assets.service.IAssetLocationService;
import com.npc.common.modular.assets.entity.AssetLocation;

import java.util.Arrays;

/**
 * <p>
 * 资产位置表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@RestController
@RequestMapping("/assetLocation")
// @Api(value = "/assetLocation", description = "资产位置表 相关接口")
public class AssetLocationController {
    
    private static final Logger logger = LoggerFactory.getLogger(AssetLocationController.class);

    @Autowired
    public IAssetLocationService assetLocationService;


    /**
     * 保存、修改 【区分id即可】
     * @param assetLocation 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "资产位置表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated AssetLocation assetLocation) {
        try {
            Boolean obj = assetLocationService.saveOrUpdate(assetLocation);
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
    @GetMapping("deleteAssetLocationById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean assetLocation =assetLocationService.removeById(id);
            return ServerResponseVO.success(assetLocation);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 资产位置表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteAssetLocationByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 资产位置表")
    public ServerResponseVO<?> batchDeleteAssetLocationByIdList(@RequestParam("ids") Integer[] ids) {
        assetLocationService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getAssetLocationById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 资产位置表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        AssetLocation assetLocation =assetLocationService.getById(id);
        return ServerResponseVO.success(assetLocation);
    }


    /**
     * 分页查询数据：
     * @param assetLocationDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getAssetLocationList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "资产位置表 分页查询数据")
    public ServerResponseVO<?> getAssetLocationList(@Validated AssetLocationDto assetLocationDto) {
        Page page = new Page(assetLocationDto.getPageNum(), assetLocationDto.getPageSize());
        QueryWrapper<AssetLocation> queryWrapper = new QueryWrapper(assetLocationDto);
        Page<AssetLocation> pages = assetLocationService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
