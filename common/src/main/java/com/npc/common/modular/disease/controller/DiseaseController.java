package com.npc.common.modular.disease.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.disease.dto.DiseaseDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.disease.service.IDiseaseService;
import com.npc.common.modular.disease.entity.Disease;

import java.util.Arrays;

/**
 * <p>
 * 疾病知识库表

 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/disease")
// @Api(value = "/disease", description = "疾病知识库表 相关接口")
public class DiseaseController {
    
    private static final Logger logger = LoggerFactory.getLogger(DiseaseController.class);

    @Autowired
    public IDiseaseService diseaseService;


    /**
     * 保存、修改 【区分id即可】
     * @param disease 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "疾病知识库表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated Disease disease) {
        try {
            Boolean obj = diseaseService.saveOrUpdate(disease);
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
    @GetMapping("deleteDiseaseById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean disease =diseaseService.removeById(id);
            return ServerResponseVO.success(disease);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 疾病知识库表


     * @param ids
     * @return
     */
    @GetMapping("batchDeleteDiseaseByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 疾病知识库表)
    public ServerResponseVO<?> batchDeleteDiseaseByIdList(@RequestParam("ids") Integer[] ids) {
        diseaseService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getDiseaseById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 疾病知识库表)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Disease disease =diseaseService.getById(id);
        return ServerResponseVO.success(disease);
    }


    /**
     * 分页查询数据：
     * @param diseaseDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getDiseaseList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "疾病知识库表 分页查询数据")
    public ServerResponseVO<?> getDiseaseList(@Validated DiseaseDto diseaseDto) {
        Page page = new Page(diseaseDto.getPageNum(), diseaseDto.getPageSize());
        QueryWrapper<Disease> queryWrapper = new QueryWrapper(diseaseDto);
        Page<Disease> pages = diseaseService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
