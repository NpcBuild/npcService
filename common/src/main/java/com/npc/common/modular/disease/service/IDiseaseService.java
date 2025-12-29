package com.npc.common.modular.disease.service;

import com.npc.common.modular.disease.entity.Disease;
import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.DiseaseDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

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
	•	设计 用药 + 服药提醒系统 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
public interface IDiseaseService extends IService<Disease> {
}
