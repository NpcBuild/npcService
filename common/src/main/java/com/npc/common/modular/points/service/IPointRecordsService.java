package com.npc.common.modular.points.service;

import com.npc.common.modular.points.entity.PointRecords;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.PointRecordsDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 积分变动记录表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
public interface IPointRecordsService extends IService<PointRecords> {
    List<PointRecords> getMyPointRecords(Long userId);
}
