package com.npc.common.modular.problem.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import com.npc.common.modular.tags.entity.Tags;
import com.npc.common.modular.tags.mapper.TagsMapper;
import com.npc.common.modular.tags.service.ITagsService;
import com.npc.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ITagsService tagsService;

    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    private static Map<Integer, String> TAG_MAP = new HashMap<>();

    @PostConstruct
    public void loadTagsFromDB() {
        List<Tags> list = tagsService.list();
        TAG_MAP = list.stream()
                .collect(Collectors.toMap(Tags::getId, Tags::getName));
    }

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

    @Override
    public ProblemVO translate(Problem record) {
        ProblemVO vo = new ProblemVO();
        BeanUtil.copyProperties(record, vo);
        if (!ObjectUtils.isEmpty(record.getTags())) {
            String[] split = record.getTags().split(",");
            List<String> tagNameList = new ArrayList<>();
            for (String tag : split) {
                tagNameList.add(TAG_MAP.get(Integer.valueOf(tag)));
            }
            vo.setTagName(String.join(",", tagNameList));
        }
        return vo;
    }

    @Override
    public List<ProblemVO> translate(List<Problem> records) {
        List<ProblemVO> res = new ArrayList<>();
        for (Problem record : records) {
            res.add(translate(record));
        }
        return res;
    }
}
