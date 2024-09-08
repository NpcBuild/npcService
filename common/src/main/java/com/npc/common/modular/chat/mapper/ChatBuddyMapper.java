package com.npc.common.modular.chat.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.chat.entity.ChatBuddy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.chat.vo.BuddyVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@Mapper
public interface ChatBuddyMapper extends BaseMapper<ChatBuddy> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<ChatBuddy> getChatBuddyListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    IPage<ChatBuddy> getList(Page<ChatBuddy> page,@Param("dto")  BuddyVO buddyVO);
}
