package com.npc.common.modular.chat.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.chat.dto.ChatBuddyHabitDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.chat.service.IChatBuddyHabitService;
import com.npc.common.modular.chat.entity.ChatBuddyHabit;

import java.util.Arrays;

/**
 * <p>
 * 人物习惯与行为模式 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/chatBuddyHabit")
// @Api(value = "/chatBuddyHabit", description = "人物习惯与行为模式 相关接口")
public class ChatBuddyHabitController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyHabitController.class);

    @Autowired
    public IChatBuddyHabitService chatBuddyHabitService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatBuddyHabit 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物习惯与行为模式 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ChatBuddyHabit chatBuddyHabit) {
        try {
            Boolean obj = chatBuddyHabitService.saveOrUpdate(chatBuddyHabit);
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
    @GetMapping("deleteChatBuddyHabitById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean chatBuddyHabit =chatBuddyHabitService.removeById(id);
            return ServerResponseVO.success(chatBuddyHabit);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 人物习惯与行为模式
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatBuddyHabitByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 人物习惯与行为模式")
    public ServerResponseVO<?> batchDeleteChatBuddyHabitByIdList(@RequestParam("ids") Integer[] ids) {
        chatBuddyHabitService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getChatBuddyHabitById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 人物习惯与行为模式 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatBuddyHabit chatBuddyHabit =chatBuddyHabitService.getById(id);
        return ServerResponseVO.success(chatBuddyHabit);
    }


    /**
     * 分页查询数据：
     * @param chatBuddyHabitDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatBuddyHabitList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物习惯与行为模式 分页查询数据")
    public ServerResponseVO<?> getChatBuddyHabitList(@Validated ChatBuddyHabitDto chatBuddyHabitDto) {
        Page page = new Page(chatBuddyHabitDto.getPageNum(), chatBuddyHabitDto.getPageSize());
        QueryWrapper<ChatBuddyHabit> queryWrapper = new QueryWrapper(chatBuddyHabitDto);
        Page<ChatBuddyHabit> pages = chatBuddyHabitService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
