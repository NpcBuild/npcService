package com.npc.pay.payment;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author NPC
 * @description
 * @create 2024/5/17 9:24
 */
@Component
public class WechatPayStrategy implements PaymentStrategy {

    @Override
    public void pay(double amount, HttpServletResponse httpServletResponse) {
        System.out.println("使用微信支付：" + amount + "元");
//        实现微信支付的具体逻辑
    }
}
