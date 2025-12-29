package com.npc.common.modular.chat.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.chat.dto.ChatBuddyCareerDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.chat.service.IChatBuddyCareerService;
import com.npc.common.modular.chat.entity.ChatBuddyCareer;

import java.util.Arrays;

/**
 * <p>
 * 人物-职业信息 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/chatBuddyCareer")
// @Api(value = "/chatBuddyCareer", description = "人物-职业信息 相关接口")
public class ChatBuddyCareerController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyCareerController.class);

    @Autowired
    public IChatBuddyCareerService chatBuddyCareerService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatBuddyCareer 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物-职业信息 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ChatBuddyCareer chatBuddyCareer) {
        try {
            Boolean obj = chatBuddyCareerService.saveOrUpdate(chatBuddyCareer);
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
    @GetMapping("deleteChatBuddyCareerById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean chatBuddyCareer =chatBuddyCareerService.removeById(id);
            return ServerResponseVO.success(chatBuddyCareer);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 人物-职业信息
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatBuddyCareerByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 人物-职业信息")
    public ServerResponseVO<?> batchDeleteChatBuddyCareerByIdList(@RequestParam("ids") Integer[] ids) {
        chatBuddyCareerService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getChatBuddyCareerById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 人物-职业信息 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatBuddyCareer chatBuddyCareer =chatBuddyCareerService.getById(id);
        return ServerResponseVO.success(chatBuddyCareer);
    }


    /**
     * 分页查询数据：
     * @param chatBuddyCareerDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatBuddyCareerList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物-职业信息 分页查询数据")
    public ServerResponseVO<?> getChatBuddyCareerList(@Validated ChatBuddyCareerDto chatBuddyCareerDto) {
        Page page = new Page(chatBuddyCareerDto.getPageNum(), chatBuddyCareerDto.getPageSize());
        QueryWrapper<ChatBuddyCareer> queryWrapper = new QueryWrapper(chatBuddyCareerDto);
        Page<ChatBuddyCareer> pages = chatBuddyCareerService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
