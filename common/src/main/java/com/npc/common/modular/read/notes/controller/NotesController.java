package com.npc.common.modular.read.notes.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.read.notes.dto.NotesDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.read.notes.service.INotesService;
import com.npc.common.modular.read.notes.entity.Notes;

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
@RequestMapping("/notes")
public class NotesController {
    
    private static final Logger logger = LoggerFactory.getLogger(NotesController.class);

    @Autowired
    public INotesService notesService;


    /**
     * 保存、修改 【区分id即可】
     * @param notes 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @Operation(summary = "添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated Notes notes) {
        try {
            Boolean obj = notesService.saveOrUpdate(notes);
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
    @GetMapping("deleteNotesById")
//    @Operation(summary = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean notes =notesService.removeById(id);
            return ServerResponseVO.success(notes);
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
    @GetMapping("batchDeleteNotesByIdList")
    @Transactional(rollbackFor = Exception.class)
//    @Operation(summary = "批量删除 ")
    public ServerResponseVO<?> batchDeleteNotesByIdList(@RequestParam("ids") Integer[] ids) {
        notesService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getNotesById", method = RequestMethod.GET)
//    @Operation(summary = "通过Id 获取  ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Notes notes =notesService.getById(id);
        return ServerResponseVO.success(notes);
    }


    /**
     * 分页查询数据：
     * @param notesDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getNotesList", method = RequestMethod.GET)
//    @Operation(summary = "分页查询数据")
    public ServerResponseVO<?> getNotesList(@Validated NotesDto notesDto) {
        Page page = new Page(notesDto.getPageNum(), notesDto.getPageSize());
        QueryWrapper<Notes> queryWrapper = new QueryWrapper(notesDto);
        Page<Notes> pages = notesService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
