package com.npc.common.modular.serverErrorLog.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.serverErrorLog.dto.ServerErrorLogDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.serverErrorLog.service.IServerErrorLogService;
import com.npc.common.modular.serverErrorLog.entity.ServerErrorLog;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-10-25
 */
@RestController
@RequestMapping("/serverErrorLog")
// @Api(value = "/serverErrorLog", description = " 相关接口")
public class ServerErrorLogController {
    
    private static final Logger logger = LoggerFactory.getLogger(ServerErrorLogController.class);

    @Autowired
    public IServerErrorLogService serverErrorLogService;


    /**
     * 保存、修改 【区分id即可】
     * @param serverErrorLog 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = " 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ServerErrorLog serverErrorLog) {
        try {
            Boolean obj = serverErrorLogService.saveOrUpdate(serverErrorLog);
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
    @GetMapping("deleteServerErrorLogById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean serverErrorLog =serverErrorLogService.removeById(id);
            return ServerResponseVO.success(serverErrorLog);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteServerErrorLogByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 ")
    public ServerResponseVO<?> batchDeleteServerErrorLogByIdList(@RequestParam("ids") Integer[] ids) {
        serverErrorLogService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getServerErrorLogById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取  ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ServerErrorLog serverErrorLog =serverErrorLogService.getById(id);
        return ServerResponseVO.success(serverErrorLog);
    }


    /**
     * 分页查询数据：
     * @param serverErrorLogDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getServerErrorLogList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = " 分页查询数据")
    public ServerResponseVO<?> getServerErrorLogList(@Validated ServerErrorLogDto serverErrorLogDto) {
        Page page = new Page(serverErrorLogDto.getPageNum(), serverErrorLogDto.getPageSize());
        QueryWrapper<ServerErrorLog> queryWrapper = new QueryWrapper(serverErrorLogDto);
        // 添加倒序排序，假设按照id字段倒序排列
        queryWrapper.orderByDesc("id");
        Page<ServerErrorLog> pages = serverErrorLogService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
