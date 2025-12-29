package com.npc.common.modular.disease.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.disease.dto.DiseaseRecordDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.disease.service.IDiseaseRecordService;
import com.npc.common.modular.disease.entity.DiseaseRecord;

import java.util.Arrays;

/**
 * <p>
 * 用户疾病记录表 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@RestController
@RequestMapping("/diseaseRecord")
// @Api(value = "/diseaseRecord", description = "用户疾病记录表 相关接口")
public class DiseaseRecordController {
    
    private static final Logger logger = LoggerFactory.getLogger(DiseaseRecordController.class);

    @Autowired
    public IDiseaseRecordService diseaseRecordService;


    /**
     * 保存、修改 【区分id即可】
     * @param diseaseRecord 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "用户疾病记录表 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated DiseaseRecord diseaseRecord) {
        try {
            Boolean obj = diseaseRecordService.saveOrUpdate(diseaseRecord);
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
    @GetMapping("deleteDiseaseRecordById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean diseaseRecord =diseaseRecordService.removeById(id);
            return ServerResponseVO.success(diseaseRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 用户疾病记录表
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteDiseaseRecordByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 用户疾病记录表")
    public ServerResponseVO<?> batchDeleteDiseaseRecordByIdList(@RequestParam("ids") Integer[] ids) {
        diseaseRecordService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getDiseaseRecordById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 用户疾病记录表 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        DiseaseRecord diseaseRecord =diseaseRecordService.getById(id);
        return ServerResponseVO.success(diseaseRecord);
    }


    /**
     * 分页查询数据：
     * @param diseaseRecordDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getDiseaseRecordList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "用户疾病记录表 分页查询数据")
    public ServerResponseVO<?> getDiseaseRecordList(@Validated DiseaseRecordDto diseaseRecordDto) {
        Page page = new Page(diseaseRecordDto.getPageNum(), diseaseRecordDto.getPageSize());
        QueryWrapper<DiseaseRecord> queryWrapper = new QueryWrapper(diseaseRecordDto);
        Page<DiseaseRecord> pages = diseaseRecordService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
