package com.npc.common.modular.location.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.location.dto.LocationDto;
import com.npc.common.todo.entity.Todo;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.location.entity.Location;
import com.npc.common.modular.location.mapper.LocationMapper;
import com.npc.common.modular.location.service.ILocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2024-06-23
 */
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements ILocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
    @Resource
    private LocationMapper locationMapper;

    @Override
    public Page<Location> getLocationList(LocationDto locationDto) {
        Page<Location> page = new Page<>(locationDto.getPageNum(), locationDto.getPageSize());
        return locationMapper.findList(page, locationDto);
    }
}
