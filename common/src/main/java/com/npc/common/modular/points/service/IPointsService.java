package com.npc.common.modular.points.service;

import com.npc.common.modular.points.entity.Points;
import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.PointsDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 用户积分余额表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
public interface IPointsService extends IService<Points> {
    Points getMyPoints(Long userId);

    /**
     * 变更积分
     * @param userId
     * @param points 变动的积分值，正数或负数
     * @param source 变动来源（如：签到、兑换、任务、后台调整）
     * @return
     */
    boolean changePoints(Long userId, Integer points,  String source);
}
