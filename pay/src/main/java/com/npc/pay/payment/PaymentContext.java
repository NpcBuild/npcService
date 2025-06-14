package com.npc.pay.payment;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author NPC
 * @description
 * @create 2024/5/17 9:31
 */
@Component
@Setter
public class PaymentContext {

    @Autowired
    private PaymentStrategy paymentStrategy;

    public void executePayment(double amount, HttpServletResponse httpServletResponse) throws IOException {
        paymentStrategy.pay(amount, httpServletResponse);
    }
}
