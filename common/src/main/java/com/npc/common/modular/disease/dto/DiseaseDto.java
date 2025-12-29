package com.npc.common.modular.disease.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 疾病知识库表

这张表是你以后这些功能的知识基石：
	•	AI：「你最近的症状与 高血压 风险相关」
	•	健康评分系统
	•	用药 & 复诊提醒
	•	疾病 → 生活方式建议
	•	体检报告自动解读
后续扩展：
	•	设计 症状表（symptom）
	•	设计 用户疾病 / 诊断记录表
	•	设计 健康风险评估表（AI）
	•	设计 用药 + 服药提醒系统
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
public class DiseaseDto extends PageSearch {

    private Integer id;  // 疾病ID 

    private String name;  // 疾病名称（中文） 

    private String nameEn;  // 疾病英文名 

    private String alias;  // 别名（逗号分隔） 

    private String icd10Code;  // ICD-10 国际疾病编码 

    private String category;  // 疾病大类（如 呼吸系统疾病） 

    private String subCategory;  // 疾病子类 

    private String description;  // 疾病简介 

    private String cause;  // 病因 

    private String symptoms;  // 常见症状（文字描述） 

    private String complications;  // 并发症 

    private String diagnosis;  // 诊断方式 

    private String treatment;  // 治疗方式 

    private String prognosis;  // 预后情况 

    private String prevention;  // 预防措施 

    private Boolean contagious;  // 是否传染病 

    private Boolean chronic;  // 是否慢性病 

    private Integer severityLevel;  // 严重程度等级(1-5) 

    private String commonPopulation;  // 高发人群 

    private String commonAgeRange;  // 高发年龄段 

    private String referenceSource;  // 参考来源（指南/文献） 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
