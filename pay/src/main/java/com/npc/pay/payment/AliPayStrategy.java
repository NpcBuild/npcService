package com.npc.pay.payment;

import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description
 * @create 2024/5/17 9:24
 */
@Component
public class AliPayStrategy implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("使用支付宝支付：" + amount + "元");
//        实现支付宝支付的具体逻辑
    }
}
