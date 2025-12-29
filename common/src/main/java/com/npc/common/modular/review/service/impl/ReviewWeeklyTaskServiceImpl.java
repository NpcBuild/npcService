package com.npc.common.modular.review.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ReviewWeeklyTaskDto;


import com.npc.common.modular.review.entity.ReviewWeeklyTask;
import com.npc.common.modular.review.mapper.ReviewWeeklyTaskMapper;
import com.npc.common.modular.review.service.IReviewWeeklyTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 每周复盘-计划任务表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Service
public class ReviewWeeklyTaskServiceImpl extends ServiceImpl<ReviewWeeklyTaskMapper, ReviewWeeklyTask> implements IReviewWeeklyTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewWeeklyTaskServiceImpl.class);
}
