package com.npc.common.modular.sysMessage.mapper;

import com.npc.common.modular.sysMessage.entity.SysMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 系统消息 / 通知中心表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-12-18
 */
@Mapper
public interface SysMessageMapper extends BaseMapper<SysMessage> {
    
	/**
     * 通过 系统消息 / 通知中心表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<SysMessage> getSysMessageListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
