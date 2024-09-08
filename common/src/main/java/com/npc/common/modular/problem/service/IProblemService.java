package com.npc.common.modular.problem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.problem.dto.ProblemDto;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.common.modular.problem.vo.ProblemVO;

import java.util.List;

/**
 * <p>
 * 问题及解决方案 服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-21
 */
public interface IProblemService extends IService<Problem> {

    IPage<Problem> selectListByPage(ProblemVO problemVO);

    boolean updateSolutionById(Problem problem);

    List<Problem> search(ProblemDto problem);
}
