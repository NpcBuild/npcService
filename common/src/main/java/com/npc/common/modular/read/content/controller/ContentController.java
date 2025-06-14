package com.npc.common.modular.read.content.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.read.content.dto.ContentDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.read.content.service.IContentService;
import com.npc.common.modular.read.content.entity.Content;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@RestController
@RequestMapping("/content")
public class ContentController {
    
    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    public IContentService contentService;


    /**
     * 保存、修改 【区分id即可】
     * @param content 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @Operation(summary = "添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated Content content) {
        try {
            Boolean obj = contentService.saveOrUpdate(content);
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
    @GetMapping("deleteContentById")
//    @Operation(summary = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean content =contentService.removeById(id);
            return ServerResponseVO.success(content);
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
    @GetMapping("batchDeleteContentByIdList")
    @Transactional(rollbackFor = Exception.class)
//    @Operation(summary = "批量删除 ")
    public ServerResponseVO<?> batchDeleteContentByIdList(@RequestParam("ids") Integer[] ids) {
        contentService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getContentById", method = RequestMethod.GET)
//    @Operation(summary = "通过Id 获取  ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Content content =contentService.getById(id);
        return ServerResponseVO.success(content);
    }


    /**
     * 分页查询数据：
     * @param contentDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getContentList", method = RequestMethod.GET)
//    @Operation(summary = "分页查询数据")
    public ServerResponseVO<?> getContentList(@Validated ContentDto contentDto) {
        Page page = new Page(contentDto.getPageNum(), contentDto.getPageSize());
        QueryWrapper<Content> queryWrapper = new QueryWrapper(contentDto);
        Page<Content> pages = contentService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
