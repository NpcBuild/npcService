package com.npc.common.modular.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.message.entity.Message;
import com.npc.common.modular.message.vo.ChatMessageVO;
import com.npc.common.modular.message.vo.UserMessageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-05-02
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Message> getMessageListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<UserMessageVO> findUserMessageByUserId(@Param("userId") Long userId);

    List<ChatMessageVO> findChatMessageById(@Param("userId") Long userId,@Param("tId") Long tId);

    void readMessage(@Param("userId") Long userId,@Param("tId") Long tId);

}
