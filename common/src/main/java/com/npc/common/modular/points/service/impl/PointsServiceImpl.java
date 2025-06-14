package com.npc.common.modular.points.service.impl;

import com.npc.common.modular.points.entity.PointRecords;
import com.npc.common.modular.points.service.IPointRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.PointsDto;


import com.npc.common.modular.points.entity.Points;
import com.npc.common.modular.points.mapper.PointsMapper;
import com.npc.common.modular.points.service.IPointsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 用户积分余额表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Service
public class PointsServiceImpl extends ServiceImpl<PointsMapper, Points> implements IPointsService {

    @Autowired
    private IPointRecordsService  pointRecordsService;

    private static final Logger logger = LoggerFactory.getLogger(PointsServiceImpl.class);

    @Override
    public Points getMyPoints(Long userId) {
        return baseMapper.getMyPoints(userId);
    }

    @Override
    public boolean changePoints(Long userId, Integer points, String source) {
        Points pointsEntity = new Points();
        pointsEntity.setUserId(userId);
        Points myPoints = getMyPoints(userId);
        if (myPoints == null) {
            myPoints = new Points();
        }
        Integer oldPoint = ObjectUtils.isEmpty(myPoints.getTotalPoints()) ? 0 : myPoints.getTotalPoints();
        pointsEntity.setId(myPoints.getId());
        pointsEntity.setTotalPoints(points + oldPoint);
        PointRecords pointRecords = new PointRecords();
        pointRecords.setUserId(userId);
        pointRecords.setPoints(points);
        pointRecords.setSource(source);
        pointRecords.setChangeType(points > 0 ? "income" : "expend");
        pointRecords.setBeforePoints(oldPoint);
        pointRecords.setAfterPoints(pointsEntity.getTotalPoints());
        pointRecords.setRemark(points > 0 ? "积分充值" : "积分消费");
        pointRecordsService.save(pointRecords);
        return saveOrUpdate(pointsEntity);
    }
}
