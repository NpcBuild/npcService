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
 * 用户疾病记录表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Data
@TableName("t_disease_record")
public class DiseaseRecord implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "主键ID")
    private Integer id;
    // @ApiModelProperty(value = "buddyId")
    private Integer buddyId;



    /**
     * 疾病ID（关联疾病库）
     */
    // @ApiModelProperty(value = "疾病ID（关联疾病库）")
    private Integer diseaseId;



    /**
     * 疾病名称（冗余字段）
     */
    // @ApiModelProperty(value = "疾病名称（冗余字段）")
    private String diseaseName;



    /**
     * 诊断日期
     */
    // @ApiModelProperty(value = "诊断日期")
    private LocalDateTime diagnosisDate;



    /**
     * 疾病状态(active/recovering/recovered/chronic)
     */
    // @ApiModelProperty(value = "疾病状态(active/recovering/recovered/chronic)")
    private String status;



    /**
     * 用户自述症状（JSON 数组）
     */
    // @ApiModelProperty(value = "用户自述症状（JSON 数组）")
    private String userSymptoms;



    /**
     * 医生诊断
     */
    // @ApiModelProperty(value = "医生诊断")
    private String doctorDiagnosis;



    /**
     * 治疗方案
     */
    // @ApiModelProperty(value = "治疗方案")
    private String treatmentPlan;



    /**
     * 用药信息（JSON 数组）
     */
    // @ApiModelProperty(value = "用药信息（JSON 数组）")
    private String medications;



    /**
     * 复诊日期
     */
    // @ApiModelProperty(value = "复诊日期")
    private LocalDateTime followUpDate;



    /**
     * 备注
     */
    // @ApiModelProperty(value = "备注")
    private String notes;

}
