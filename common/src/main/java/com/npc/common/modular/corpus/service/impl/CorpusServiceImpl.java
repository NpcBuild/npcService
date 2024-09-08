package com.npc.common.modular.corpus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.corpus.entity.Corpus;
import com.npc.common.modular.corpus.mapper.CorpusMapper;
import com.npc.common.modular.corpus.service.ICorpusService;
import com.npc.common.modular.corpus.vo.CorpusInfoVO;
import com.npc.common.modular.corpus.vo.CorpusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 语料 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-10-15
 */
@Service
public class CorpusServiceImpl extends ServiceImpl<CorpusMapper, Corpus> implements ICorpusService {

    private static final Logger logger = LoggerFactory.getLogger(CorpusServiceImpl.class);

    /**获取列表分页*/
    @Override
    public IPage<Corpus> selectListByPage(CorpusVO corpusVO) {
        // 创建分页对象
        Page<Corpus> page = new Page<>(corpusVO.getPageNum(), corpusVO.getPageSize());
        corpusVO.setFlag(0);
        QueryWrapper queryWrapper = new QueryWrapper<>(corpusVO);
        queryWrapper.orderByDesc("cre_time");
        // 执行分页查询，将查询结果封装到分页对象中
        IPage<Corpus> corpusPage = this.baseMapper.selectPage(page, queryWrapper);
        return corpusPage;
    }

    @Override
    public CorpusInfoVO getInfo(CorpusVO vo) {
        return this.baseMapper.selectInfo(vo);
    }

    @Override
    public Corpus getRand(CorpusVO vo) {
        return this.baseMapper.selectRand(vo);
    }

    @Override
    public Boolean score(String id) {
        return this.baseMapper.score(id);
    }
}
