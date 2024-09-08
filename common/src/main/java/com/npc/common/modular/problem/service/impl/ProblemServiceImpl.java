package com.npc.common.modular.problem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.problem.dto.ProblemDto;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.common.modular.problem.mapper.ProblemMapper;
import com.npc.common.modular.problem.service.IProblemService;
import com.npc.common.modular.problem.vo.ProblemVO;
import com.npc.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 问题及解决方案 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-21
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    /**获取列表分页*/
    @Override
    public IPage<Problem> selectListByPage(ProblemVO problemVO) {
        // 创建分页对象
        Page<Problem> page = new Page<>(problemVO.getPageNum(), problemVO.getPageSize());

        IPage<Problem> corpusPage = this.baseMapper.getList(problemVO, page);
        return corpusPage;
    }

    @Override
    public boolean updateSolutionById(Problem problem) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",problem.getId());
        boolean update = this.update(problem, updateWrapper);
        return update;
    }

    @Override
    public List<Problem> search(ProblemDto problem) {
        List<Problem> problemList = this.baseMapper.search(problem);
        return problemList;
    }
}
