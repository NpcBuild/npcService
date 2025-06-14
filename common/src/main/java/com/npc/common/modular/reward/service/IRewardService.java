package com.npc.common.modular.reward.service;

import com.npc.common.modular.reward.entity.Reward;
import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.RewardDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 奖励定义表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
public interface IRewardService extends IService<Reward> {
    boolean exchange(Reward reward);
}
