package com.npc.common.modular.money.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.money.dto.MoneyDto;
import com.npc.common.modular.money.entity.Money;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.money.vo.MoneyReport;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-12-25
 */
@Mapper
public interface MoneyMapper extends BaseMapper<Money> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Money> getMoneyListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

    /**
     * 获取信息
     * @param money
     * @return
     */
    MoneyReport getInfo(Money money);

    IPage<Money> getList(Page<Money> page, @Param("dto") MoneyDto dto);
}
