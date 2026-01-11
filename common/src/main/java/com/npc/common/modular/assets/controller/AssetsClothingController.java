package com.npc.common.modular.assets.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.assets.dto.AssetsClothingDto;
import com.npc.common.modular.assets.vo.AssetsClothingVO;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.assets.service.IAssetsClothingService;
import com.npc.common.modular.assets.entity.AssetsClothing;

import java.util.Arrays;

/**
 * <p>
 * 衣物资产扩展表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2026-01-04
 */
@RestController
@RequestMapping("/assetsClothing")
// @Api(value = "/assetsClothing", description = "衣物资产扩展表 相关接口")
public class AssetsClothingController {
    
    private static final Logger logger = LoggerFactory.getLogger(AssetsClothingController.class);

    @Autowired
    public IAssetsClothingService assetsClothingService;


    /**
     * 保存、修改 【区分id即可】
     * @param assetsClothing 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "衣物资产扩展表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated AssetsClothing assetsClothing) {
        try {
            Boolean obj = assetsClothingService.saveOrUpdate(assetsClothing);
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
    @GetMapping("deleteAssetsClothingById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean assetsClothing =assetsClothingService.removeById(id);
            return ServerResponseVO.success(assetsClothing);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 衣物资产扩展表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteAssetsClothingByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 衣物资产扩展表")
    public ServerResponseVO<?> batchDeleteAssetsClothingByIdList(@RequestParam("ids") Integer[] ids) {
        assetsClothingService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getAssetsClothingById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 衣物资产扩展表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        AssetsClothing assetsClothing =assetsClothingService.getById(id);
        return ServerResponseVO.success(assetsClothing);
    }


    /**
     * 分页查询数据：
     * @param assetsClothingDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getAssetsClothingList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "衣物资产扩展表 分页查询数据")
    public ServerResponseVO<?> getAssetsClothingList(@Validated AssetsClothingDto assetsClothingDto) {
        Page page = new Page(assetsClothingDto.getPageNum(), assetsClothingDto.getPageSize());
        QueryWrapper<AssetsClothing> queryWrapper = new QueryWrapper(assetsClothingDto);
        Page<AssetsClothing> pages = assetsClothingService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
    /**
     * 分页查询数据：
     * @param assetsClothingDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getAssetsClothing", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "衣物资产扩展表 分页查询数据")
    public ServerResponseVO<?> getAssetsClothing(@Validated AssetsClothingDto assetsClothingDto) {
        IPage<AssetsClothingVO> assetsClothingIPage = assetsClothingService.getList(assetsClothingDto);
        return ServerResponseVO.success(assetsClothingIPage);
    }
}
