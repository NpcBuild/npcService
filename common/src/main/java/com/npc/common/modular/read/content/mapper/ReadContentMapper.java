package com.npc.common.modular.read.content.mapper;

import com.npc.common.modular.read.content.entity.ReadContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-10-12
 */
@Mapper
public interface ReadContentMapper extends BaseMapper<ReadContent> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<ReadContent> getReadContentListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
