package com.npc.common.modular.stock.redis;

import com.npc.common.modular.stock.model.result.StockResult;
import com.npc.common.modular.stock.service.StockService;
import com.npc.common.utils.spring.StartLog;
import com.npc.redis.utils.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class RedisPreheatRunner implements ApplicationRunner {

    @Autowired
    private StockService stockService;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        //从数据库查询热卖商品
        StockResult stock = stockService.getStockById(1);
        StartLog.log("开始预热数据到Redis");
//        log.info("开始预热数据到Redis");

        // 删除旧缓存
        RedisPoolUtil.del(RedisKeysConstant.STOCK_COUNT + stock.getCount());
        RedisPoolUtil.del(RedisKeysConstant.STOCK_SALE + stock.getSale());
        RedisPoolUtil.del(RedisKeysConstant.STOCK_VERSION + stock.getVersion());
        //缓存预热
        int sid = stock.getId();
        RedisPoolUtil.set(RedisKeysConstant.STOCK_COUNT + sid, String.valueOf(stock.getCount()));
        RedisPoolUtil.set(RedisKeysConstant.STOCK_SALE + sid, String.valueOf(stock.getSale()));
        RedisPoolUtil.set(RedisKeysConstant.STOCK_VERSION + sid, String.valueOf(stock.getVersion()));

    }
}
