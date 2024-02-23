package com.npc.common.modular.corpus.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.corpus.entity.Corpus;
import com.npc.common.modular.corpus.service.ICorpusService;
import com.npc.common.modular.corpus.vo.CorpusVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * <p>
 * 语料 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-10-15
 */
@RestController
@RequestMapping("/corpus")
public class CorpusController {
    
    private static final Logger logger = LoggerFactory.getLogger(CorpusController.class);

    @Autowired
    public ICorpusService corpusService;


    /**
     * 保存、修改 【区分id即可】
     * @param corpus 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Corpus corpus) {
        try {
            Boolean obj = corpusService.save(corpus);
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
    @GetMapping("deleteCorpusById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean corpus =corpusService.removeById(id);
            return ServerResponseVO.success(corpus);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 语料
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteCorpusByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteCorpusByIdList(@RequestParam("ids") Integer[] ids) {

            corpusService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success("批量删除成功");
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getCorpusById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Corpus corpus =corpusService.getById(id);
        return ServerResponseVO.success(corpus);
    }


    /**
     * 分页查询数据：
     * @param corpusVO 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getCorpusList", method = RequestMethod.GET)
    public ServerResponseVO<?> getCorpusList(@Validated CorpusVO corpusVO) {
        IPage<Corpus> page = corpusService.selectListByPage(corpusVO);
        return ServerResponseVO.success(page);
    }
}
