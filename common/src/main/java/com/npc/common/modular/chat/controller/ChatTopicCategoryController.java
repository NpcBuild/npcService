package com.npc.common.modular.chat.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.chat.dto.ChatTopicCategoryDto;
import com.npc.common.modular.chat.vo.CategoryTreeVo;
import com.npc.common.modular.plan.dto.PlanDto;
import com.npc.common.modular.plan.vo.PlanTreeVo;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.chat.service.IChatTopicCategoryService;
import com.npc.common.modular.chat.entity.ChatTopicCategory;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 聊天话题分类表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-05-05
 */
@RestController
@RequestMapping("/chatTopicCategory")
//@Api(value = "/chatTopicCategory", description = "聊天话题分类表 相关接口")
public class ChatTopicCategoryController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatTopicCategoryController.class);

    @Autowired
    public IChatTopicCategoryService chatTopicCategoryService;


    /**
     * 保存、修改 【区分id即可】
     * @param chatTopicCategory 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @ApiOperation(response = ServerResponseVO.class, value = "聊天话题分类表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated ChatTopicCategory chatTopicCategory) {
        try {
            Boolean obj = chatTopicCategoryService.saveOrUpdate(chatTopicCategory);
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
    @GetMapping("deleteChatTopicCategoryById")
//    @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean chatTopicCategory =chatTopicCategoryService.removeById(id);
            return ServerResponseVO.success(chatTopicCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 聊天话题分类表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteChatTopicCategoryByIdList")
    @Transactional(rollbackFor = Exception.class)
//    @ApiOperation(response = ServerResponseVO.class, value = "批量删除 聊天话题分类表")
    public ServerResponseVO<?> batchDeleteChatTopicCategoryByIdList(@RequestParam("ids") Integer[] ids) {
        chatTopicCategoryService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getChatTopicCategoryById", method = RequestMethod.GET)
//    @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 聊天话题分类表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        ChatTopicCategory chatTopicCategory =chatTopicCategoryService.getById(id);
        return ServerResponseVO.success(chatTopicCategory);
    }


    /**
     * 分页查询数据：
     * @param chatTopicCategoryDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getChatTopicCategoryList", method = RequestMethod.GET)
//    @ApiOperation(response = ServerResponseVO.class, value = "聊天话题分类表 分页查询数据")
    public ServerResponseVO<?> getChatTopicCategoryList(@Validated ChatTopicCategoryDto chatTopicCategoryDto) {
        Page page = new Page(chatTopicCategoryDto.getPageNum(), chatTopicCategoryDto.getPageSize());
        QueryWrapper<ChatTopicCategory> queryWrapper = new QueryWrapper(chatTopicCategoryDto);
        Page<ChatTopicCategory> pages = chatTopicCategoryService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }

    /**
     * 分页计划树
     * @param chatTopicCategoryDto 查询对象
     * @return List 分页对象
     */
    @RequestMapping(value = "/getChatTopicCategoryTree", method = RequestMethod.GET)
    public ServerResponseVO<?> getPlanTree(@Validated ChatTopicCategoryDto chatTopicCategoryDto) {
        List<CategoryTreeVo> categoryTreeVos = chatTopicCategoryService.getTree(chatTopicCategoryDto);
        return ServerResponseVO.success(categoryTreeVos);
    }
}
