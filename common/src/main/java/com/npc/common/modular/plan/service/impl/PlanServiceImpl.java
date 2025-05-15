package com.npc.common.modular.plan.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.plan.entity.Plan;
import com.npc.common.modular.plan.mapper.PlanMapper;
import com.npc.common.modular.plan.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * <p>
 * 曼陀罗计划表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2024-07-20
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements IPlanService {

    private static final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

    /**
     * 查询所有的计划根节点
     * @return
     */
    @Override
    public List<Plan> getPlanRoot() {
        List<Plan> plans = baseMapper.getPlanRoot();
        return plans;
    }
}
