package com.npc.common.modular.career.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 解决方案 / 创业项目表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@Data
public class SolutionProjectDto extends PageSearch {

    private Integer id;  // 解决方案ID 

    private String title;  // 项目标题 

    private String description;  // 项目描述 

    private Integer linkedProblemId;  // 关联的社会问题ID(可为空) 

    private String stage;  // 项目阶段 

    private Integer impactScore;  // 影响力评分(1-5) 

    private Integer effortScore;  // 投入评估(1-5) 

    private Integer feasibility;  // 可行性评分(1-5) 

    private String traction;  // 关键进展 

    private String nextAction;  // 下一步行动 

    private String tags;  // 标签，逗号分隔 

    private Boolean favorite;  // 是否收藏 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
