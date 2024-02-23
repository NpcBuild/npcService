package com.npc.common.modular.stock.controller;

import com.npc.common.modular.stock.redis.limit.RedisOrderLimit;
import com.npc.common.modular.stock.service.StockOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wow
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class StockController {

    @Autowired
    private StockOrderService stockOrderService;


    @GetMapping("/buy")
    public String buy(){
        try {
            if (RedisOrderLimit.limit()) {
                stockOrderService.createOrderAsync(1);
            }
        } catch (Exception e) {
            log.error("创建订单失败" + e);
        }
        return "秒杀请求正在处理，排队中";
    }
}
