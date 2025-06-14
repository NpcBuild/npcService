package com.npc.common.modular.plan.service.impl;

import com.npc.common.modular.plan.dto.PlanDto;
import com.npc.common.modular.plan.vo.PlanTreeVo;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.plan.entity.Plan;
import com.npc.common.modular.plan.mapper.PlanMapper;
import com.npc.common.modular.plan.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;

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

    @Override
    public List<PlanTreeVo> getTree(PlanDto planDto) {
        Integer id = planDto.getId();
        Integer level = planDto.getLevel();
        List<Plan> planList = this.getBaseMapper().getTree(id, level);
        return buildTree(planList, id);
    }

    public static List<PlanTreeVo> buildTree(List<Plan> list, Integer rootId) {
        // 数据类型转换
        List<PlanTreeVo> voList = new ArrayList<>();
        for (Plan tags : list) {
            PlanTreeVo vo = new PlanTreeVo();
            vo.setId(tags.getId());
            vo.setContent(tags.getContent());
            vo.setParentId(tags.getParentId());
            voList.add(vo);
        }

        Map<Integer, PlanTreeVo> nodeMap = new HashMap<>();
        List<PlanTreeVo> rootNodes = new ArrayList<>();
        for (PlanTreeVo node : voList) {
            nodeMap.put(node.getId(), node);
            if (Objects.equals(node.getParentId(), rootId)) {
                rootNodes.add(node);
            }
        }

        for (PlanTreeVo node : voList) {
            PlanTreeVo parentNode = nodeMap.get(node.getParentId());
            if (parentNode!= null) {
                if (parentNode.getChildren() == null) {
                    List<PlanTreeVo> tagsVOList = new ArrayList<>();
                    parentNode.setChildren(tagsVOList);
                }
                parentNode.getChildren().add(node);
            }
        }
        return rootNodes;
    }

    /**
     * 查询所有的计划根节点
     * @return
     */
    @Override
    public List<Plan> getPlanRoot() {
        List<Plan> plans = baseMapper.getPlanRoot();
        return plans;
    }

    /**
     * 更新节点位置等信息
     * @param planList
     */
    @Override
    public void updatePlanPosition(List<Plan> planList) {
        baseMapper.updatePlanPosition(planList);
    }
}
