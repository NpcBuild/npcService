package com.npc.common.modular.plan.vo;

import com.npc.common.modular.plan.entity.Plan;
import lombok.Data;

import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2025/5/1 17:29
 */
@Data
public class PlanTreeVo extends Plan {
    private List<PlanTreeVo> children;
}
