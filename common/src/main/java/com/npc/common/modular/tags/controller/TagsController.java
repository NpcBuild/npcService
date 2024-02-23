package com.npc.common.modular.tags.controller;

import com.npc.common.modular.tags.entity.Tags;
import com.npc.common.modular.tags.service.ITagsService;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-20
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
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Tags tags) {
        try {
            boolean obj = tagsService.saveOrUpdate(tags);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ResponseDataModel转换结果
     */
    @GetMapping("deleteTagsById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean tags =tagsService.removeById(id);
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
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getTagsById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Tags tags =tagsService.getById(id);
        return ServerResponseVO.success(tags);
    }


//    /**
//     * 分页查询数据：
//     * @param tagsDto 查询对象
//     * @return PageList 分页对象
//     */
//    @RequestMapping(value = "/getTagsList", method = RequestMethod.GET)
//    public ServerResponseVO<?> getTagsList(@Validated TagsDto tagsDto) {
//        IPage<Tags> page = tagsService.getTagsList(tagsDto);
//        return ServerResponseVO.success(page);
//    }
}
