package com.npc.common.modular.assets.mapper;

import com.npc.common.modular.assets.entity.AssetLocation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 资产位置表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Mapper
public interface AssetLocationMapper extends BaseMapper<AssetLocation> {
    
	/**
     * 通过 资产位置表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<AssetLocation> getAssetLocationListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
