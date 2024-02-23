package com.npc.common.modular.quartzJob.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.quartzJob.entity.QuartzJob;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 定时任务表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-09-06
 */
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {
    
	/**
     * 通过 定时任务表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<QuartzJob> getQuartzJobListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);
    QuartzJob getByJobName(@Param("jobName") String jobName);

}
