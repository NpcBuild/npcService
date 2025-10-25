package com.npc.common.modular.money.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.money.dto.MoneyPointDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.money.service.IMoneyPointService;
import com.npc.common.modular.money.entity.MoneyPoint;

import java.util.Arrays;

/**
 * <p>
 * 金额记录点 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@RestController
@RequestMapping("/moneyPoint")
// @Api(value = "/moneyPoint", description = "金额记录点 相关接口")
public class MoneyPointController {
    
    private static final Logger logger = LoggerFactory.getLogger(MoneyPointController.class);

    @Autowired
    public IMoneyPointService moneyPointService;


    /**
     * 保存、修改 【区分id即可】
     * @param moneyPoint 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "金额记录点 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated MoneyPoint moneyPoint) {
        try {
            Boolean obj = moneyPointService.saveOrUpdate(moneyPoint);
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
    @GetMapping("deleteMoneyPointById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean moneyPoint =moneyPointService.removeById(id);
            return ServerResponseVO.success(moneyPoint);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 金额记录点
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteMoneyPointByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 金额记录点")
    public ServerResponseVO<?> batchDeleteMoneyPointByIdList(@RequestParam("ids") Integer[] ids) {
        moneyPointService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getMoneyPointById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 金额记录点 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        MoneyPoint moneyPoint =moneyPointService.getById(id);
        return ServerResponseVO.success(moneyPoint);
    }


    /**
     * 分页查询数据：
     * @param moneyPointDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getMoneyPointList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "金额记录点 分页查询数据")
    public ServerResponseVO<?> getMoneyPointList(@Validated MoneyPointDto moneyPointDto) {
        Page page = new Page(moneyPointDto.getPageNum(), moneyPointDto.getPageSize());
        QueryWrapper<MoneyPoint> queryWrapper = new QueryWrapper(moneyPointDto);
        Page<MoneyPoint> pages = moneyPointService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
