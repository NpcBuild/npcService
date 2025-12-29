package com.npc.common.modular.chat.mapper;

import com.npc.common.modular.chat.entity.ChatBuddyPast;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 人物过往经历 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Mapper
public interface ChatBuddyPastMapper extends BaseMapper<ChatBuddyPast> {
    
	/**
     * 通过 人物过往经历 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<ChatBuddyPast> getChatBuddyPastListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<ChatBuddyPast> getByBuddyId(@Param("id") int id);
}
