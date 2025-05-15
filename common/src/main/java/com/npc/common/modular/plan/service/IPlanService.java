package com.npc.common.modular.plan.service;

import com.npc.common.modular.plan.entity.Plan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 曼陀罗计划表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2024-07-20
 */
public interface IPlanService extends IService<Plan> {
    List<Plan> getPlanRoot();
}
