package com.npc.common.modular.problem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.common.modular.problem.service.IProblemService;
import com.npc.common.modular.problem.vo.ProblemVO;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 问题及解决方案 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    public IProblemService problemService;


    /**
     * 保存、修改 【区分id即可】
     * @param problem 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Problem problem) {
        try {
            boolean obj = problemService.saveOrUpdate(problem);
            return ServerResponseVO.success(problem);
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
    @GetMapping("deleteProblemById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean problem =problemService.removeById(id);
            return ServerResponseVO.success(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 问题及解决方案
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteProblemByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteProblemByIdList(@RequestParam("ids") Integer[] ids) {

        problemService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getProblemById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Problem problem =problemService.getById(id);
        return ServerResponseVO.success(problem);
    }

    /**
     * 搜索问题（普通搜索、ES搜索）
     * @param text
     * @return
     */
    @GetMapping("/search")
    public ServerResponseVO<?> search(String text) {
        List<Problem> problems = problemService.search(text);
        return ServerResponseVO.success(problems);
    }


    /**
     * 分页查询数据：
     * @param problemVO 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getProblemList", method = RequestMethod.GET)
    public ServerResponseVO<?> getProblemList(@Validated ProblemVO problemVO) {
        IPage<Problem> page = problemService.selectListByPage(problemVO);
        return ServerResponseVO.success(page);
    }
}
