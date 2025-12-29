package com.npc.common.modular.review.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.ReviewMonthlyDto;


import com.npc.common.modular.review.entity.ReviewMonthly;
import com.npc.common.modular.review.mapper.ReviewMonthlyMapper;
import com.npc.common.modular.review.service.IReviewMonthlyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 每月复盘表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Service
public class ReviewMonthlyServiceImpl extends ServiceImpl<ReviewMonthlyMapper, ReviewMonthly> implements IReviewMonthlyService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewMonthlyServiceImpl.class);
}
