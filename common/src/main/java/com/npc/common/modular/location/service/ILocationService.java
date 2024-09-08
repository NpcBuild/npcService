package com.npc.common.modular.location.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.location.dto.LocationDto;
import com.npc.common.modular.location.entity.Location;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2024-06-23
 */
public interface ILocationService extends IService<Location> {
    Page<Location> getLocationList(LocationDto locationDto);
}
