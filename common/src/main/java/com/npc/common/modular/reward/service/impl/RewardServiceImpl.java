package com.npc.common.modular.reward.service.impl;

import com.npc.common.modular.points.service.IPointsService;
import com.npc.common.modular.reward.entity.Reward;
import com.npc.common.modular.reward.entity.RewardClaimLog;
import com.npc.common.modular.reward.service.IRewardClaimLogService;
import com.npc.common.modular.reward.service.IRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.RewardDto;


import com.npc.common.modular.reward.mapper.RewardMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 奖励定义表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Service
public class RewardServiceImpl extends ServiceImpl<RewardMapper, Reward> implements IRewardService {

    @Autowired
    private IPointsService pointsService;
    @Autowired
    private IRewardClaimLogService rewardClaimLogService;

    private static final Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);

    @Override
    public boolean exchange(Reward reward) {
        Reward rewardInfo = getById(reward.getId());
        try {
            // 积分兑换
            if ("points".equals(rewardInfo.getType())) {
                if (rewardInfo.getPointsRequired() != null && rewardInfo.getPointsRequired() > 0) {
                    if (pointsService.changePoints(1L, -rewardInfo.getPointsRequired(), "兑换了" + rewardInfo.getName() + "奖励")) {
                        logger.info("用户兑换奖励成功");
                        RewardClaimLog rewardClaimLog = new RewardClaimLog();
                        rewardClaimLog.setUserId(1L);
                        rewardClaimLog.setRewardId(rewardInfo.getId());
                        rewardClaimLog.setPointsUsed(rewardInfo.getPointsRequired());
                        rewardClaimLog.setStatus("granted");
                        rewardClaimLogService.save(rewardClaimLog);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
