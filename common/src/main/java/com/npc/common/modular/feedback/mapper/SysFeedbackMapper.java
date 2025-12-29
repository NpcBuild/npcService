package com.npc.common.modular.feedback.mapper;

import com.npc.common.modular.feedback.entity.SysFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 用户反馈信息表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-11-06
 */
@Mapper
public interface SysFeedbackMapper extends BaseMapper<SysFeedback> {
    
	/**
     * 通过 用户反馈信息表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<SysFeedback> getSysFeedbackListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    // 统计每月的反馈数量
    List<SysFeedback> countFeedbackByMonth();
    // 查找待处理的高优先级反馈
    List<SysFeedback> findPendingHighPriorityFeedback();
}
