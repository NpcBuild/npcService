package com.npc.common.modular.career.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.career.dto.SolutionProjectDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.career.service.ISolutionProjectService;
import com.npc.common.modular.career.entity.SolutionProject;

import java.util.Arrays;

/**
 * <p>
 * 解决方案 / 创业项目表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@RestController
@RequestMapping("/solutionProject")
// @Api(value = "/solutionProject", description = "解决方案 / 创业项目表 相关接口")
public class SolutionProjectController {
    
    private static final Logger logger = LoggerFactory.getLogger(SolutionProjectController.class);

    @Autowired
    public ISolutionProjectService solutionProjectService;


    /**
     * 保存、修改 【区分id即可】
     * @param solutionProject 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "解决方案 / 创业项目表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated SolutionProject solutionProject) {
        try {
            Boolean obj = solutionProjectService.saveOrUpdate(solutionProject);
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
    @GetMapping("deleteSolutionProjectById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean solutionProject =solutionProjectService.removeById(id);
            return ServerResponseVO.success(solutionProject);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 解决方案 / 创业项目表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteSolutionProjectByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 解决方案 / 创业项目表")
    public ServerResponseVO<?> batchDeleteSolutionProjectByIdList(@RequestParam("ids") Integer[] ids) {
        solutionProjectService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getSolutionProjectById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 解决方案 / 创业项目表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        SolutionProject solutionProject =solutionProjectService.getById(id);
        return ServerResponseVO.success(solutionProject);
    }


    /**
     * 分页查询数据：
     * @param solutionProjectDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getSolutionProjectList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "解决方案 / 创业项目表 分页查询数据")
    public ServerResponseVO<?> getSolutionProjectList(@Validated SolutionProjectDto solutionProjectDto) {
        Page page = new Page(solutionProjectDto.getPageNum(), solutionProjectDto.getPageSize());
        QueryWrapper<SolutionProject> queryWrapper = new QueryWrapper(solutionProjectDto);
        Page<SolutionProject> pages = solutionProjectService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
