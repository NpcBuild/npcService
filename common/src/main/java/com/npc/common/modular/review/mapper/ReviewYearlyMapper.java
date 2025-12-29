package com.npc.common.modular.review.mapper;

import com.npc.common.modular.review.entity.ReviewYearly;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 年度复盘表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-12-08
 */
@Mapper
public interface ReviewYearlyMapper extends BaseMapper<ReviewYearly> {
    
	/**
     * 通过 年度复盘表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<ReviewYearly> getReviewYearlyListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
