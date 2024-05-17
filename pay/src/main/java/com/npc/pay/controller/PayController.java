package com.npc.pay.controller;

import com.npc.pay.payment.AliPayStrategy;
import com.npc.pay.payment.PaymentContext;
import com.npc.pay.payment.WechatPayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NPC
 * @description
 * @create 2024/5/17 9:16
 */
@RestController
public class PayController {

    @Autowired
    private PaymentContext paymentContext;

    @GetMapping("/pay")
    public String pay(@RequestParam double amount, @RequestParam String method) {
        // 根据支付方式选择具体的支付策略
        switch (method) {
            case "alipay":
                paymentContext.setPaymentStrategy(new AliPayStrategy());
                break;
            case "wechatpay":
                paymentContext.setPaymentStrategy(new WechatPayStrategy());
                break;
            default:
                throw new IllegalArgumentException("Unknown strategy:" + method);
//                return "Unsupported payment method";
        }

//        执行支付
        paymentContext.executePayment(amount);
        return "Payment successful";
    }
}
