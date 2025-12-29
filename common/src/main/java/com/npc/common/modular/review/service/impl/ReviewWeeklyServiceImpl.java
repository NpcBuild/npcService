package com.npc.common.modular.review.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ReviewWeeklyDto;


import com.npc.common.modular.review.entity.ReviewWeekly;
import com.npc.common.modular.review.mapper.ReviewWeeklyMapper;
import com.npc.common.modular.review.service.IReviewWeeklyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 每周复盘表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Service
public class ReviewWeeklyServiceImpl extends ServiceImpl<ReviewWeeklyMapper, ReviewWeekly> implements IReviewWeeklyService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewWeeklyServiceImpl.class);
}
