package com.npc.common.modular.tags.controller;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.tags.dto.TagsDto;
import com.npc.common.modular.tags.vo.TagsVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.tags.service.ITagsService;
import com.npc.common.modular.tags.entity.Tags;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2024-07-01
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    private static final Logger logger = LoggerFactory.getLogger(TagsController.class);

    @Autowired
    public ITagsService tagsService;


    /**
     * 保存、修改 【区分id即可】
     * @param tags 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Tags tags) {
        try {
            Boolean obj = tagsService.saveOrUpdate(tags);
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
    @GetMapping("deleteTagsById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean tags =tagsService.removeById(id);
            return ServerResponseVO.success(tags);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 标签表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteTagsByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteTagsByIdList(@RequestParam("ids") Integer[] ids) {
        tagsService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getTagsById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Tags tags =tagsService.getById(id);
        return ServerResponseVO.success(tags);
    }


    /**
     * 分页查询数据：
     * @param tagsDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getTagsList", method = RequestMethod.GET)
    public ServerResponseVO<?> getTagsList(@Validated TagsDto tagsDto) {
        Page page = new Page(tagsDto.getPageNum(), tagsDto.getPageSize());
        Tags tags = new Tags();
        BeanUtil.copyProperties(tagsDto, tags);
        QueryWrapper<Tags> queryWrapper = new QueryWrapper(tags);
        Page<Tags> pages = tagsService.page(page,queryWrapper);
        return ServerResponseVO.success(pages);
    }


    /**
     * TODO 获取树：
     * @param tagsDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getTagsTree", method = RequestMethod.GET)
    public ServerResponseVO<?> getTagsTree(@Validated TagsDto tagsDto) {
        List<TagsVO> pages = tagsService.getTree(tagsDto.getId(),tagsDto.getLevel());
        return ServerResponseVO.success(pages);
    }
}
