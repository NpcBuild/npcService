package com.npc.common.modular.reward.service.impl;

import com.npc.common.modular.reward.entity.RewardClaimLog;
import com.npc.common.modular.reward.service.IRewardClaimLogService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.RewardClaimLogDto;


import com.npc.common.modular.reward.mapper.RewardClaimLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 奖励兑换记录表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Service
public class RewardClaimLogServiceImpl extends ServiceImpl<RewardClaimLogMapper, RewardClaimLog> implements IRewardClaimLogService {

    private static final Logger logger = LoggerFactory.getLogger(RewardClaimLogServiceImpl.class);
}
