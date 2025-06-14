package com.npc.pay.payment;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.npc.pay.properties.AliPayProperties;
import com.npc.pay.vo.AliPayVO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author NPC
 * @description
 * @create 2024/5/17 9:24
 */
@Component
@Primary
public class AliPayStrategy implements PaymentStrategy {

    @Resource
    private AliPayProperties aliPayProperties;
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";

    @Override
    public void pay(double amount, HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("使用支付宝支付：" + amount + "元");
        // 实现支付宝支付的具体逻辑
        // 1.根据支付宝的配置生成一个支付客户端 客户端用于去调用支付宝的API
        aliPayProperties = new AliPayProperties();
        aliPayProperties.setAppId("9021000144617577");
        aliPayProperties.setAppPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC3aJG2C6CY0ns3Ye2dq2+K5BlqZEQeRZN+OcXJL3CT53DFR+czXNQ+ISRGubjiJ/nXzJbOhMfrfcqGUxnAehgwk7TC6reMjHzjMC/c6fGJixOyN0WKVqTq0Khg4BHcHiLjl+p2OlDiG7nRZG7H4KHoow88+NRTyd2pBDB0GzN6CnwZ7aldbzpvLg29s7GPHlmArQnZPBQIEFS+IJmR9lD0aXMfXhGbVWsX3TKcx50mG0OxVlNAhGLbodpn144FQS1U+dbtZ31Qq3AS76A0ml7BREc2ht7xpeV63777jEQ6SQUgCT/K6RuERmdGmkLq/awkW5E2ScqcnjIQPv0ugsr7AgMBAAECggEALdqt+2aiyzJsbedE4hOXepEWrZvN0AezHW7SPT9oFQJbFmeorRW7VqEwkaIjUB+Qflx/I7jcxsqXVMf3+0BLRfHX0peeNjx1iNNmVAQFSE8GKJta/oA1ESQDxNygenjqXh6AFvjzeeRWq7xBWiWWNTR3PdY0N5KToYsTliOq1grVmnoY1nVNtG6gk5jT/Kf75NAmtZzpN19DzvnCmkkyDADlg5VcKf3LCfXWDH59D2HS78yKzt8GksY6WNRsKXmfpQVMrUsJVwdEtCWEkTtFkKx4AcNFlut/3R+z/RvIhQk+Czc1y+bl6jMXH7maYe3q6DN6KWhz/PqF1ElbmohbEQKBgQD5X2Z7+C9ef+lCxN+p0E0d8KZdglii/1Bj1GUK7Ve37sEiARhD/hI/gGM10A6osEDK9460952q8UQkR4JWrZ4e3PfvME4Ar/PGJOU8IaI2XIAQH3l0k5pXkJ2E/OcSjTXdaWEWJsEJ7tXXkAaqpQHbrKAA9Odk6Vd6f5ETkOjmCQKBgQC8SGIotxoD37O1NEV7oxHIWqNTzTccaPCDbns1VdSi2LU+2KNlKX0mkIqYgPf9Qju97jTiJi/s/lYxwiucBnkupO1tRKSD7FgMftc+BJEG0YJhZXcTTqNwaDn9rFdkYQFLrENPKx8udJkNyVhkxKiia/o100GooID3bHG6/sSJ4wKBgBM5I3FTBHPIcHbRLlyszgUM1AZJUgPeopfPe6qH+UcYhJLWesRjBY0XnI49Z6PhUl1TEBErNWBEEPiq+oUsBRBkAEjT9G9hIO2ZUoJ8JUEDiGu1wWsXJcKyRzJFPJrVf919jhpjCmZ1ns5rL9hft/DzMbJhLqU8x52Nz+UuOoXhAoGBAIRoQt9vJJRk1PQxdkukOfU0CHf+cStBgSJsp6iyW37J92vi3cVej+x26YCl8b/gvDKtOfNv93QqyGLlsdVfmAj9YxrckvVTV/gxmxy6CWNKIu5rdHnXa4i5Q07py+uDbG3TDuK16I+634eaeNcklZLjbGo9zobPmOJnGPPwBjcRAoGAZrBVpJS95hOo15/i/QShoW4R16JadjWBJKp7oYLAqTmxYif8oDU17FB8W92rGuaW7uijesF+Ny7bF1bWoOaFvxKwLObEOtQ5YN+Ehd+Rlxp21VsfwE7NWykh9Py5YsPDb2L3VphiIqfMm4wOnuUo/HIg8QjiMWhxWwosf11S0MM=");
        aliPayProperties.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt2iRtgugmNJ7N2HtnatviuQZamREHkWTfjnFyS9wk+dwxUfnM1zUPiEkRrm44if518yWzoTH633KhlMZwHoYMJO0wuq3jIx84zAv3OnxiYsTsjdFilak6tCoYOAR3B4i45fqdjpQ4hu50WRux+Ch6KMPPPjUU8ndqQQwdBszegp8Ge2pXW86by4NvbOxjx5ZgK0J2TwUCBBUviCZkfZQ9GlzH14Rm1VrF90ynMedJhtDsVZTQIRi26HaZ9eOBUEtVPnW7Wd9UKtwEu+gNJpewURHNobe8aXlet+++4xEOkkFIAk/yukbhEZnRppC6v2sJFuRNknKnJ4yED79LoLK+wIDAQAB");
        aliPayProperties.setNotifyUrl("");
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayProperties.getAppId(), aliPayProperties.getAppPrivateKey(), FORMAT, CHARSET, aliPayProperties.getAlipayPublicKey(), SIGN_TYPE);
        // 2.创建一个支付请求对象
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(aliPayProperties.getNotifyUrl());
        AliPayVO vo = new AliPayVO();
        String out_trade_no = UUID.randomUUID().toString();
        vo.setOut_trade_no(out_trade_no);
        BigDecimal total_amount = new BigDecimal(amount);
        String subject = "支付测试";
        String body = "一个商品";
        request.setBizContent("{'out_trade_no':'" + out_trade_no + "',"
                                + "'total_amount':'" + total_amount + "',"
                + "'subject':'" + subject + "',"+ "'body':'"+ body +"',"
                + "'product_code':'FAST_INSTANT_TRADE_PAY'}");
        request.setReturnUrl("http://localhost:1314/api/pay");
        // 3.调用支付客户端的API完成支付，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            // 调用SDK生成表单
            // 会受到支付宝的响应，响应是一个页面，一开始是登录，然后显示金额，让用户输入密码进行付款
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpServletResponse.setContentType("text/html;charset=" + CHARSET);
        httpServletResponse.getWriter().write(form); // 直接将完整的表单html输出到页面
        httpServletResponse.getWriter().flush();
        // 4.支付成功后，支付宝会向我们的服务器发送一个异步通知，我们需要在服务器端接收这个通知，然后进行处理
    }
}
