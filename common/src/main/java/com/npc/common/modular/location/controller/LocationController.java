package com.npc.common.modular.location.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.location.dto.LocationDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.location.service.ILocationService;
import com.npc.common.modular.location.entity.Location;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2024-06-23
 */
@RestController
@RequestMapping("/location")
public class LocationController {
    
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    public ILocationService locationService;


    /**
     * 保存、修改 【区分id即可】
     * @param location 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Location location) {
        try {
            Boolean obj = locationService.saveOrUpdate(location);
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
    @GetMapping("deleteLocationById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean location =locationService.removeById(id);
            return ServerResponseVO.success(location);
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
    @GetMapping("batchDeleteLocationByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteLocationByIdList(@RequestParam("ids") Integer[] ids) {
        locationService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getLocationById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Location location =locationService.getById(id);
        return ServerResponseVO.success(location);
    }


    /**
     * 分页查询数据：
     * @param locationDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getLocationList", method = RequestMethod.GET)
    public ServerResponseVO<?> getLocationList(LocationDto locationDto) {
        Page<Location> page = locationService.getLocationList(locationDto);
        return ServerResponseVO.success(page);
    }
}
