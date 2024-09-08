package com.npc.common.modular.tags.mapper;

import com.npc.common.modular.tags.entity.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;


import java.util.List;
import java.util.Map;

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

    @Cacheable
    String getTagName(@Param("id") Integer id);
    
	/**
     * 通过 标签表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Tags> getTagsListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    /**
     * 获取树形数据
     * @param id 搜索根节点id
     * @param level 向下搜索层级
     * @return
     */
    List<Tags> getTree(@Param("id") Integer id, @Param("level") Integer level);
}
