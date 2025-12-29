package com.npc.common.modular.feedback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.SysFeedbackDto;


import com.npc.common.modular.feedback.entity.SysFeedback;
import com.npc.common.modular.feedback.mapper.SysFeedbackMapper;
import com.npc.common.modular.feedback.service.ISysFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户反馈信息表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-11-06
 */
@Service
public class SysFeedbackServiceImpl extends ServiceImpl<SysFeedbackMapper, SysFeedback> implements ISysFeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(SysFeedbackServiceImpl.class);
}
