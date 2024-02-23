package com.npc.common.modular.stock.mapper;

import com.npc.common.modular.stock.entity.StockOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StockOrderMapper {

    /**
     * 清空订单表
     * 成功为 0，失败为 -1
     */
    @Update("TRUNCATE TABLE stock_order")
    int delOrderDBBefore();

    int insertSelective (@Param("stockOrder") StockOrder stockOrder);


}
