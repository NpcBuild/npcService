package com.npc.common.modular.chat.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.chat.dto.ChatBuddyPersonalityDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.chat.service.IChatBuddyPersonalityService;
import com.npc.common.modular.chat.entity.ChatBuddyPersonality;

import java.util.Arrays;

/**
 * <p>
 * 人物性格与人格特征 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/chatBuddyPersonality")
// @Api(value = "/chatBuddyPersonality", description = "人物性格与人格特征 相关接口")
public class ChatBuddyPersonalityController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyPersonalityController.class);

    @Autowired
    public IChatBuddyPersonalityService chatBuddyPersonalityService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatBuddyPersonality 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物性格与人格特征 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ChatBuddyPersonality chatBuddyPersonality) {
        try {
            Boolean obj = chatBuddyPersonalityService.saveOrUpdate(chatBuddyPersonality);
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
    @GetMapping("deleteChatBuddyPersonalityById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean chatBuddyPersonality =chatBuddyPersonalityService.removeById(id);
            return ServerResponseVO.success(chatBuddyPersonality);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 人物性格与人格特征
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatBuddyPersonalityByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 人物性格与人格特征")
    public ServerResponseVO<?> batchDeleteChatBuddyPersonalityByIdList(@RequestParam("ids") Integer[] ids) {
        chatBuddyPersonalityService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getChatBuddyPersonalityById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 人物性格与人格特征 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatBuddyPersonality chatBuddyPersonality =chatBuddyPersonalityService.getById(id);
        return ServerResponseVO.success(chatBuddyPersonality);
    }


    /**
     * 分页查询数据：
     * @param chatBuddyPersonalityDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatBuddyPersonalityList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物性格与人格特征 分页查询数据")
    public ServerResponseVO<?> getChatBuddyPersonalityList(@Validated ChatBuddyPersonalityDto chatBuddyPersonalityDto) {
        Page page = new Page(chatBuddyPersonalityDto.getPageNum(), chatBuddyPersonalityDto.getPageSize());
        QueryWrapper<ChatBuddyPersonality> queryWrapper = new QueryWrapper(chatBuddyPersonalityDto);
        Page<ChatBuddyPersonality> pages = chatBuddyPersonalityService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
