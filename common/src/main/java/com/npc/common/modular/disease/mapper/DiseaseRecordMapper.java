package com.npc.common.modular.disease.mapper;

import com.npc.common.modular.disease.entity.DiseaseRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 用户疾病记录表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-12-26
 */
@Mapper
public interface DiseaseRecordMapper extends BaseMapper<DiseaseRecord> {
    
	/**
     * 通过 用户疾病记录表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<DiseaseRecord> getDiseaseRecordListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
