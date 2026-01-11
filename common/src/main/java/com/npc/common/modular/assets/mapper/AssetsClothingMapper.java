package com.npc.common.modular.assets.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.assets.dto.AssetsClothingDto;
import com.npc.common.modular.assets.entity.AssetsClothing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.assets.vo.AssetsClothingVO;
import com.npc.common.modular.dailySchedule.dto.DailyScheduleDto;
import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 衣物资产扩展表 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2026-01-04
 */
@Mapper
public interface AssetsClothingMapper extends BaseMapper<AssetsClothing> {
    
	/**
     * 通过 衣物资产扩展表 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<AssetsClothing> getAssetsClothingListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    IPage<AssetsClothingVO> getList(Page<AssetsClothing> page, @Param("dto") AssetsClothingDto dto);

}
