package com.npc.common.modular.assets.mapper;

import com.npc.common.modular.assets.entity.Assets;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 物品表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Mapper
public interface AssetsMapper extends BaseMapper<Assets> {
    
	/**
     * 通过 物品表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Assets> getAssetsListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    List<Assets> getMyAssetsList(int userId);
}
