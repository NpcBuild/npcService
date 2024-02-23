package com.npc.common.modular.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.stock.entity.Stock;
import com.npc.common.modular.stock.mapper.StockMapper;
import com.npc.common.modular.stock.model.result.StockResult;
import com.npc.common.modular.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper,Stock> implements StockService {
    @Override
    public StockResult getStockById(int sid) {
        return this.baseMapper.getStockById(sid);
    }


    @Override
    public int updateStockByOptimistic(Stock stock) {
        return this.baseMapper.updateByOptimistic(stock);
    }
}
