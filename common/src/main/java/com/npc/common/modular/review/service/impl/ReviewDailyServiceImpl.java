package com.npc.common.modular.review.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ReviewDailyDto;


import com.npc.common.modular.review.entity.ReviewDaily;
import com.npc.common.modular.review.mapper.ReviewDailyMapper;
import com.npc.common.modular.review.service.IReviewDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 每日复盘表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Service
public class ReviewDailyServiceImpl extends ServiceImpl<ReviewDailyMapper, ReviewDaily> implements IReviewDailyService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewDailyServiceImpl.class);
}
