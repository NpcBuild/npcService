package com.npc.pay.payment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PaymentStrategy {
    void pay(double amount, HttpServletResponse httpServletResponse) throws IOException;
}
