package com.npc.common.modular.plan.service;

import com.npc.common.modular.plan.dto.PlanDto;
import com.npc.common.modular.plan.entity.Plan;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import com.npc.common.modular.plan.vo.PlanTreeVo;

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
    List<PlanTreeVo> getTree(PlanDto planDto);
    List<Plan> getPlanRoot();

    void updatePlanPosition(@Param("list") List<Plan> planList);
}
