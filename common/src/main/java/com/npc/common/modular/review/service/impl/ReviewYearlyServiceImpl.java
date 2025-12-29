package com.npc.common.modular.review.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ReviewYearlyDto;


import com.npc.common.modular.review.entity.ReviewYearly;
import com.npc.common.modular.review.mapper.ReviewYearlyMapper;
import com.npc.common.modular.review.service.IReviewYearlyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 年度复盘表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Service
public class ReviewYearlyServiceImpl extends ServiceImpl<ReviewYearlyMapper, ReviewYearly> implements IReviewYearlyService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewYearlyServiceImpl.class);
}
