package com.npc.common.modular.points.service.impl;

import com.npc.common.modular.points.service.IPointRecordsService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.PointRecordsDto;


import com.npc.common.modular.points.entity.PointRecords;
import com.npc.common.modular.points.mapper.PointRecordsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * <p>
 * 积分变动记录表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Service
public class PointRecordsServiceImpl extends ServiceImpl<PointRecordsMapper, PointRecords> implements IPointRecordsService {

    private static final Logger logger = LoggerFactory.getLogger(PointRecordsServiceImpl.class);

    @Override
    public List<PointRecords> getMyPointRecords(Long userId) {
        return baseMapper.getMyPointRecords(userId);
    }
}
