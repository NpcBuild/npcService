package com.npc.common.modular.reward.mapper;

import com.npc.common.modular.reward.entity.RewardClaimLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 奖励兑换记录表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Mapper
public interface RewardClaimLogMapper extends BaseMapper<RewardClaimLog> {
    
	/**
     * 通过 奖励兑换记录表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<RewardClaimLog> getRewardClaimLogListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
