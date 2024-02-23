package com.npc.common.modular.corpus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.corpus.entity.Corpus;
import com.npc.common.modular.corpus.vo.CorpusVO;

/**
 * <p>
 * 语料 服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-10-15
 */
public interface ICorpusService extends IService<Corpus> {

    IPage<Corpus> selectListByPage(CorpusVO corpusVO);
}
