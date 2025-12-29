package com.npc.common.modular.disease.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class DiseaseRecordDto extends PageSearch {

    private Integer id;  // 主键ID 

    private Integer buddyId; 

    private Integer diseaseId;  // 疾病ID（关联疾病库） 

    private String diseaseName;  // 疾病名称（冗余字段） 

    private LocalDateTime diagnosisDate;  // 诊断日期 

    private String status;  // 疾病状态(active/recovering/recovered/chronic) 

    private String userSymptoms;  // 用户自述症状（JSON 数组） 

    private String doctorDiagnosis;  // 医生诊断 

    private String treatmentPlan;  // 治疗方案 

    private String medications;  // 用药信息（JSON 数组） 

    private LocalDateTime followUpDate;  // 复诊日期 

    private String notes;  // 备注 

}
