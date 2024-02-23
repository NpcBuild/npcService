package com.npc.common.modular.corpus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.corpus.entity.Corpus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 语料 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-10-15
 */
@Mapper
public interface CorpusMapper extends BaseMapper<Corpus> {
    
	/**
     * 通过 语料 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Corpus> getCorpusListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
