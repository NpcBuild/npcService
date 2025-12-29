package com.npc.common.modular.disease.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("t_disease")
public class Disease implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 疾病ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "疾病ID")
    private Integer id;



    /**
     * 疾病名称（中文）
     */
    // @ApiModelProperty(value = "疾病名称（中文）")
    private String name;



    /**
     * 疾病英文名
     */
    // @ApiModelProperty(value = "疾病英文名")
    private String nameEn;



    /**
     * 别名（逗号分隔）
     */
    // @ApiModelProperty(value = "别名（逗号分隔）")
    private String alias;



    /**
     * ICD-10 国际疾病编码
     */
    // @ApiModelProperty(value = "ICD-10 国际疾病编码")
    private String icd10Code;



    /**
     * 疾病大类（如 呼吸系统疾病）
     */
    // @ApiModelProperty(value = "疾病大类（如 呼吸系统疾病）")
    private String category;



    /**
     * 疾病子类
     */
    // @ApiModelProperty(value = "疾病子类")
    private String subCategory;



    /**
     * 疾病简介
     */
    // @ApiModelProperty(value = "疾病简介")
    private String description;



    /**
     * 病因
     */
    // @ApiModelProperty(value = "病因")
    private String cause;



    /**
     * 常见症状（文字描述）
     */
    // @ApiModelProperty(value = "常见症状（文字描述）")
    private String symptoms;



    /**
     * 并发症
     */
    // @ApiModelProperty(value = "并发症")
    private String complications;



    /**
     * 诊断方式
     */
    // @ApiModelProperty(value = "诊断方式")
    private String diagnosis;



    /**
     * 治疗方式
     */
    // @ApiModelProperty(value = "治疗方式")
    private String treatment;



    /**
     * 预后情况
     */
    // @ApiModelProperty(value = "预后情况")
    private String prognosis;



    /**
     * 预防措施
     */
    // @ApiModelProperty(value = "预防措施")
    private String prevention;



    /**
     * 是否传染病
     */
    // @ApiModelProperty(value = "是否传染病")
    private Boolean contagious;



    /**
     * 是否慢性病
     */
    // @ApiModelProperty(value = "是否慢性病")
    private Boolean chronic;



    /**
     * 严重程度等级(1-5)
     */
    // @ApiModelProperty(value = "严重程度等级(1-5)")
    private Integer severityLevel;



    /**
     * 高发人群
     */
    // @ApiModelProperty(value = "高发人群")
    private String commonPopulation;



    /**
     * 高发年龄段
     */
    // @ApiModelProperty(value = "高发年龄段")
    private String commonAgeRange;



    /**
     * 参考来源（指南/文献）
     */
    // @ApiModelProperty(value = "参考来源（指南/文献）")
    private String referenceSource;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 更新时间
     */
    // @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

}
