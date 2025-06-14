package com.npc.common.modular.chat.mapper;

import com.npc.common.modular.chat.entity.ChatTopicCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 聊天话题分类表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-05-05
 */
@Mapper
public interface ChatTopicCategoryMapper extends BaseMapper<ChatTopicCategory> {
    
	/**
     * 通过 聊天话题分类表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<ChatTopicCategory> getChatTopicCategoryListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<ChatTopicCategory> getTree(@Param("id") Integer id,@Param("level")  Integer level);
}
