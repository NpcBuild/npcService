package com.npc.common.modular.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.stock.entity.Stock;
import com.npc.common.modular.stock.model.result.StockResult;

public interface StockService extends IService<Stock> {
    StockResult getStockById(int sid);


    /**
     * 乐观锁更新库存，解决超卖问题
     */
    int updateStockByOptimistic(Stock stock);
}
