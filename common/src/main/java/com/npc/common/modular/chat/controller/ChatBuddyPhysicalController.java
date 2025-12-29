package com.npc.common.modular.chat.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.chat.dto.ChatBuddyPhysicalDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.chat.service.IChatBuddyPhysicalService;
import com.npc.common.modular.chat.entity.ChatBuddyPhysical;

import java.util.Arrays;

/**
 * <p>
 * 人物身体与外貌特征 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/chatBuddyPhysical")
// @Api(value = "/chatBuddyPhysical", description = "人物身体与外貌特征 相关接口")
public class ChatBuddyPhysicalController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatBuddyPhysicalController.class);

    @Autowired
    public IChatBuddyPhysicalService chatBuddyPhysicalService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatBuddyPhysical 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物身体与外貌特征 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ChatBuddyPhysical chatBuddyPhysical) {
        try {
            Boolean obj = chatBuddyPhysicalService.saveOrUpdate(chatBuddyPhysical);
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
    @GetMapping("deleteChatBuddyPhysicalById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean chatBuddyPhysical =chatBuddyPhysicalService.removeById(id);
            return ServerResponseVO.success(chatBuddyPhysical);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 人物身体与外貌特征
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatBuddyPhysicalByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 人物身体与外貌特征")
    public ServerResponseVO<?> batchDeleteChatBuddyPhysicalByIdList(@RequestParam("ids") Integer[] ids) {
        chatBuddyPhysicalService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getChatBuddyPhysicalById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 人物身体与外貌特征 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatBuddyPhysical chatBuddyPhysical =chatBuddyPhysicalService.getById(id);
        return ServerResponseVO.success(chatBuddyPhysical);
    }


    /**
     * 分页查询数据：
     * @param chatBuddyPhysicalDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatBuddyPhysicalList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "人物身体与外貌特征 分页查询数据")
    public ServerResponseVO<?> getChatBuddyPhysicalList(@Validated ChatBuddyPhysicalDto chatBuddyPhysicalDto) {
        Page page = new Page(chatBuddyPhysicalDto.getPageNum(), chatBuddyPhysicalDto.getPageSize());
        QueryWrapper<ChatBuddyPhysical> queryWrapper = new QueryWrapper(chatBuddyPhysicalDto);
        Page<ChatBuddyPhysical> pages = chatBuddyPhysicalService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
