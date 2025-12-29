package com.npc.common.modular.chat.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.chat.dto.ChatBuddyPastDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.chat.service.IChatBuddyPastService;
import com.npc.common.modular.chat.entity.ChatBuddyPast;

import java.util.Arrays;

/**
 * <p>
 * 人物过往经历 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/chatBuddyPast")
// @Api(value = "/chatBuddyPast", description = "人物过往经历 相关接口")
public class ChatBuddyPastController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyPastController.class);

    @Autowired
    public IChatBuddyPastService chatBuddyPastService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatBuddyPast 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物过往经历 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ChatBuddyPast chatBuddyPast) {
        try {
            Boolean obj = chatBuddyPastService.saveOrUpdate(chatBuddyPast);
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
    @GetMapping("deleteChatBuddyPastById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean chatBuddyPast =chatBuddyPastService.removeById(id);
            return ServerResponseVO.success(chatBuddyPast);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 人物过往经历
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatBuddyPastByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 人物过往经历")
    public ServerResponseVO<?> batchDeleteChatBuddyPastByIdList(@RequestParam("ids") Integer[] ids) {
        chatBuddyPastService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getChatBuddyPastById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 人物过往经历 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatBuddyPast chatBuddyPast =chatBuddyPastService.getById(id);
        return ServerResponseVO.success(chatBuddyPast);
    }


    /**
     * 分页查询数据：
     * @param chatBuddyPastDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatBuddyPastList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物过往经历 分页查询数据")
    public ServerResponseVO<?> getChatBuddyPastList(@Validated ChatBuddyPastDto chatBuddyPastDto) {
        Page page = new Page(chatBuddyPastDto.getPageNum(), chatBuddyPastDto.getPageSize());
        QueryWrapper<ChatBuddyPast> queryWrapper = new QueryWrapper(chatBuddyPastDto);
        Page<ChatBuddyPast> pages = chatBuddyPastService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
