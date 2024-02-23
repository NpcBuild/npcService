package com.npc.common.modular.diary.controller;

import com.npc.common.modular.diary.entity.Diary;
import com.npc.common.modular.diary.service.IDiaryService;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@RestController
@RequestMapping("/diary")
public class DiaryController {
    
    private static final Logger logger = LoggerFactory.getLogger(DiaryController.class);

    @Autowired
    public IDiaryService diaryService;


    /**
     * 保存、修改 【区分id即可】
     * @param diary 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Diary diary) {
        try {
            boolean obj = diaryService.saveOrUpdate(diary);
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
    @GetMapping("deleteDiaryById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean diary =diaryService.removeById(id);
            return ServerResponseVO.success(diary);
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
    @GetMapping("batchDeleteDiaryByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteDiaryByIdList(@RequestParam("ids") Integer[] ids) {

        diaryService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getDiaryById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Diary diary =diaryService.getById(id);
        return ServerResponseVO.success(diary);
    }


    /**
     * 通过时间 获取对象
     * @param diary 查询条件
     * @return ResponseDataModel转换结果
     */
    @PostMapping("/getDiaryByDate")
    public ServerResponseVO<?> getByDate(@RequestBody Diary diary) {
        Map<String,Object> map = new HashMap<>();
        map.put("date",diary.getDate());
        List<Diary> diaries =diaryService.listByMap(map);
        return ServerResponseVO.success(diaries.get(0));
    }


//    /**
//     * 分页查询数据：
//     * @param diary 查询对象
//     * @return PageList 分页对象
//     */
//    @RequestMapping(value = "/getDiaryList", method = RequestMethod.GET)
//    public ServerResponseVO<?> getDiaryList(@Validated Diary diary) {
//        Page<Diary> page = diaryService.selectListByPage(diary);
//        return ServerResponseVO.success(page);
//    }
}
