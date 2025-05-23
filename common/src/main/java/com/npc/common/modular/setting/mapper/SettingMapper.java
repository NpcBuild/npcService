package com.npc.common.modular.setting.mapper;

import com.npc.common.modular.setting.entity.Setting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2024-06-09
 */
@Mapper
public interface SettingMapper extends BaseMapper<Setting> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Setting> getSettingListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
