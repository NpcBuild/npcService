package com.npc.common.modular.disease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.DiseaseRecordDto;


import com.npc.common.modular.disease.entity.DiseaseRecord;
import com.npc.common.modular.disease.mapper.DiseaseRecordMapper;
import com.npc.common.modular.disease.service.IDiseaseRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户疾病记录表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Service
public class DiseaseRecordServiceImpl extends ServiceImpl<DiseaseRecordMapper, DiseaseRecord> implements IDiseaseRecordService {

    private static final Logger logger = LoggerFactory.getLogger(DiseaseRecordServiceImpl.class);
}
