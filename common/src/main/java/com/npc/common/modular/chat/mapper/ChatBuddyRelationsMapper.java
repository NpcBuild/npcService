package com.npc.common.modular.chat.mapper;

import com.npc.common.modular.chat.entity.ChatBuddyRelations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 朋友关系 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-11-26
 */
@Mapper
public interface ChatBuddyRelationsMapper extends BaseMapper<ChatBuddyRelations> {
    
	/**
     * 通过 朋友关系 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<ChatBuddyRelations> getChatBuddyRelationsListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
