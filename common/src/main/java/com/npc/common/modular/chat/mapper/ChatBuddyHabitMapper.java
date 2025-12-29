package com.npc.common.modular.chat.mapper;

import com.npc.common.modular.chat.entity.ChatBuddyHabit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 人物习惯与行为模式 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Mapper
public interface ChatBuddyHabitMapper extends BaseMapper<ChatBuddyHabit> {
    
	/**
     * 通过 人物习惯与行为模式 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<ChatBuddyHabit> getChatBuddyHabitListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<ChatBuddyHabit> getByBuddyId(@Param("id") int id);
}
