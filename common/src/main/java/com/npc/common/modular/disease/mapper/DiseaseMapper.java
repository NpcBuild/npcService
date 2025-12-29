package com.npc.common.modular.disease.mapper;

import com.npc.common.modular.disease.entity.Disease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

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
	•	设计 用药 + 服药提醒系统 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Mapper
public interface DiseaseMapper extends BaseMapper<Disease> {
    
	/**
     * 通过 疾病知识库表

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
	•	设计 用药 + 服药提醒系统 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Disease> getDiseaseListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
