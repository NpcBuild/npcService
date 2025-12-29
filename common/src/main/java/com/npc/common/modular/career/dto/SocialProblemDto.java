package com.npc.common.modular.career.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 社会问题池
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@Data
public class SocialProblemDto extends PageSearch {

    private Integer id;  // 问题ID 

    private String title;  // 问题标题 

    private String description;  // 问题描述 

    private String category;  // 问题分类 

    private Integer severity;  // 严重程度(1-5) 

    private String affectedPopulation;  // 受影响人群 

    private String frequency;  // 发生频率描述 

    private String currentSolutions;  // 现有解决方案 

    private Integer pain;  // 痛点程度(0-5) 

    private Integer purchasingPower;  // 购买力(0-5) 

    private Integer reachability;  // 触达难度(0-5) 

    private Integer marketGrowth;  // 市场增长(0-5) 

    private Integer totalScore;  // 总评分(计算字段) 

    private String tags;  // 标签，逗号分隔 

    private String notes;  // 补充笔记 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
