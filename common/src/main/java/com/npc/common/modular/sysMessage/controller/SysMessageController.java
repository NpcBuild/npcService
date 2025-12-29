package com.npc.common.modular.sysMessage.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.sysMessage.dto.SysMessageDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.sysMessage.service.ISysMessageService;
import com.npc.common.modular.sysMessage.entity.SysMessage;

import java.util.Arrays;

/**
 * <p>
 * 系统消息 / 通知中心表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-18
 */
@RestController
@RequestMapping("/sysMessage")
// @Api(value = "/sysMessage", description = "系统消息 / 通知中心表 相关接口")
public class SysMessageController {
    
    private static final Logger logger = LoggerFactory.getLogger(SysMessageController.class);

    @Autowired
    public ISysMessageService sysMessageService;


    /**
     * 保存、修改 【区分id即可】
     * @param sysMessage 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "系统消息 / 通知中心表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated SysMessage sysMessage) {
        try {
            Boolean obj = sysMessageService.saveOrUpdate(sysMessage);
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
    @GetMapping("deleteSysMessageById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean sysMessage =sysMessageService.removeById(id);
            return ServerResponseVO.success(sysMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 系统消息 / 通知中心表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteSysMessageByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 系统消息 / 通知中心表")
    public ServerResponseVO<?> batchDeleteSysMessageByIdList(@RequestParam("ids") Integer[] ids) {
        sysMessageService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getSysMessageById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 系统消息 / 通知中心表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        SysMessage sysMessage =sysMessageService.getById(id);
        return ServerResponseVO.success(sysMessage);
    }


    /**
     * 分页查询数据：
     * @param sysMessageDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getSysMessageList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "系统消息 / 通知中心表 分页查询数据")
    public ServerResponseVO<?> getSysMessageList(@Validated SysMessageDto sysMessageDto) {
        Page page = new Page(sysMessageDto.getPageNum(), sysMessageDto.getPageSize());
        QueryWrapper<SysMessage> queryWrapper = new QueryWrapper(sysMessageDto);
        Page<SysMessage> pages = sysMessageService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
