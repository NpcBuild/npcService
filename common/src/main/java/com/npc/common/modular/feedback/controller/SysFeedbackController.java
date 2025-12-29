package com.npc.common.modular.feedback.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.feedback.dto.SysFeedbackDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.feedback.service.ISysFeedbackService;
import com.npc.common.modular.feedback.entity.SysFeedback;

import java.util.Arrays;

/**
 * <p>
 * 用户反馈信息表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-11-06
 */
@RestController
@RequestMapping("/sysFeedback")
// @Api(value = "/sysFeedback", description = "用户反馈信息表 相关接口")
public class SysFeedbackController {
    
    private static final Logger logger = LoggerFactory.getLogger(SysFeedbackController.class);

    @Autowired
    public ISysFeedbackService sysFeedbackService;


    /**
     * 保存、修改 【区分id即可】
     * @param sysFeedback 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "用户反馈信息表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated SysFeedback sysFeedback) {
        try {
            Boolean obj = sysFeedbackService.saveOrUpdate(sysFeedback);
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
    @GetMapping("deleteSysFeedbackById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean sysFeedback =sysFeedbackService.removeById(id);
            return ServerResponseVO.success(sysFeedback);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 用户反馈信息表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteSysFeedbackByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 用户反馈信息表")
    public ServerResponseVO<?> batchDeleteSysFeedbackByIdList(@RequestParam("ids") Integer[] ids) {
        sysFeedbackService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getSysFeedbackById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 用户反馈信息表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        SysFeedback sysFeedback =sysFeedbackService.getById(id);
        return ServerResponseVO.success(sysFeedback);
    }


    /**
     * 分页查询数据：
     * @param sysFeedbackDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getSysFeedbackList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "用户反馈信息表 分页查询数据")
    public ServerResponseVO<?> getSysFeedbackList(@Validated SysFeedbackDto sysFeedbackDto) {
        Page page = new Page(sysFeedbackDto.getPageNum(), sysFeedbackDto.getPageSize());
        QueryWrapper<SysFeedback> queryWrapper = new QueryWrapper(sysFeedbackDto);
        Page<SysFeedback> pages = sysFeedbackService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
