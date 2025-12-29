package com.npc.common.modular.career.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.SocialProblemDto;


import com.npc.common.modular.career.entity.SocialProblem;
import com.npc.common.modular.career.mapper.SocialProblemMapper;
import com.npc.common.modular.career.service.ISocialProblemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 社会问题池 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@Service
public class SocialProblemServiceImpl extends ServiceImpl<SocialProblemMapper, SocialProblem> implements ISocialProblemService {

    private static final Logger logger = LoggerFactory.getLogger(SocialProblemServiceImpl.class);
}
