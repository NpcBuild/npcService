package com.npc.common.modular.tags.mapper;

import com.npc.common.modular.tags.entity.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-12-20
 */
@Mapper
public interface TagsMapper extends BaseMapper<Tags> {
    
	/**
     * 通过 标签表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Tags> getTagsListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
