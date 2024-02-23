package com.npc.common.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.stock.entity.Stock;
import com.npc.common.modular.stock.model.result.StockResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StockMapper  extends BaseMapper<Stock> {
    StockResult getStockById (@Param("sid") int sid);
//    int updateStockById (@Param("stock") Stock stock);
    /**
     * 乐观锁 version
     */
    @Update("UPDATE t_stock SET count = count - 1, sale = sale + 1, version = version + 1 WHERE " +
            "id = #{id, jdbcType = INTEGER} AND version = #{version, jdbcType = INTEGER}")
    int updateByOptimistic(Stock stock);
}
