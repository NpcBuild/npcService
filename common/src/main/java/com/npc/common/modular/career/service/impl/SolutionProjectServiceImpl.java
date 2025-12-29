package com.npc.common.modular.career.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.SolutionProjectDto;


import com.npc.common.modular.career.entity.SolutionProject;
import com.npc.common.modular.career.mapper.SolutionProjectMapper;
import com.npc.common.modular.career.service.ISolutionProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 解决方案 / 创业项目表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@Service
public class SolutionProjectServiceImpl extends ServiceImpl<SolutionProjectMapper, SolutionProject> implements ISolutionProjectService {

    private static final Logger logger = LoggerFactory.getLogger(SolutionProjectServiceImpl.class);
}
