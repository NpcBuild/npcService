package com.npc.common.modular.points.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.points.dto.PointRecordsDto;
import com.npc.common.modular.points.entity.PointRecords;
import com.npc.common.modular.points.service.IPointRecordsService;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;

/**
 * <p>
 * 积分变动记录表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@RestController
@RequestMapping("/pointRecords")
// @Api(value = "/pointRecords", description = "积分变动记录表 相关接口")
public class PointRecordsController {
    
    private static final Logger logger = LoggerFactory.getLogger(PointRecordsController.class);

    @Autowired
    public IPointRecordsService pointRecordsService;


    /**
     * 保存、修改 【区分id即可】
     * @param pointRecords 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "积分变动记录表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated PointRecords pointRecords) {
        try {
            Boolean obj = pointRecordsService.saveOrUpdate(pointRecords);
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
    @GetMapping("deletePointRecordsById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean pointRecords =pointRecordsService.removeById(id);
            return ServerResponseVO.success(pointRecords);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 积分变动记录表
     * @param ids
     * @return
     */
    @GetMapping("batchDeletePointRecordsByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 积分变动记录表")
    public ServerResponseVO<?> batchDeletePointRecordsByIdList(@RequestParam("ids") Integer[] ids) {
        pointRecordsService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getPointRecordsById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 积分变动记录表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        PointRecords pointRecords =pointRecordsService.getById(id);
        return ServerResponseVO.success(pointRecords);
    }


    /**
     * 分页查询数据：
     * @param pointRecordsDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getPointRecordsList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "积分变动记录表 分页查询数据")
    public ServerResponseVO<?> getPointRecordsList(@Validated PointRecordsDto pointRecordsDto) {
        Page page = new Page(pointRecordsDto.getPageNum(), pointRecordsDto.getPageSize());
        QueryWrapper<PointRecords> queryWrapper = new QueryWrapper(pointRecordsDto);
        Page<PointRecords> pages = pointRecordsService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }

    /**
     * 查询我的积分兑换历史
     */
    @RequestMapping(value = "/getMyPointRecords", method = RequestMethod.GET)
    public ServerResponseVO<?> getMyPointRecords() {
        Long userId = 1L;
        return ServerResponseVO.success(pointRecordsService.getMyPointRecords(userId));
    }
}
