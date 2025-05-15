package com.npc.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.npc.pay.dto.WxPayDTO;
import com.npc.pay.payment.AliPayStrategy;
import com.npc.pay.payment.PaymentContext;
import com.npc.pay.payment.WechatPayStrategy;
import com.npc.pay.properties.WxPayProperties;
import com.npc.pay.service.PayService;
import com.npc.utils.DateUtils;
import com.npc.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ author NPC
 * @ description
 * @ create 2024/5/17 9:16
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PaymentContext paymentContext;

    @GetMapping("")
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

    @Resource
    private PayService payService;
    @Resource
    private WxPayProperties wxPayProperties;

    /**
     * 微信native下单,返回支付二维码
     * @param orderNumber 下单编号
     * @return 支付二维码
     */
    @GetMapping("/nativePay")
    public Object nativePay(@RequestParam("orderNumber") String orderNumber) {
        //todo 业务操作-根据订单编号查询订单信息
        //将订单信息中的数据存到WxPayDTO
        WxPayDTO payDTO = new WxPayDTO();
        payDTO.setBody("商品描述");
        //订单总金额，单位为分
        payDTO.setTotalFee(1);
        //支付回调地址
        payDTO.setNotifyUrl(wxPayProperties.getNotifyUrl());
        //商品订单编号
        payDTO.setOutTradeNo(orderNumber);
        //获取时间
        Date date = new Date();
        String timeStart = DateUtils.dateTimeNow();
        //结束时间设置在30分钟后
        String timeExpire = DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, DateUtils.getAfter(date, Calendar.MINUTE,30));
        //交易起始时间
        payDTO.setTimeStart(timeStart);
        //交易结束时间
        payDTO.setTimeExpire(timeExpire);
        Object url = payService.transactions(payDTO, WxPayConstants.TradeType.NATIVE);
        return url;
    }

    /**
     * 微信JSAPI下单,返回JS支付相关配置
     * @param orderNumber
     * @return
     */
    @GetMapping("/jsapiPay")
    public Object jsapiPay(@RequestParam("orderNumber") String orderNumber) {
        //todo 业务操作-根据订单编号查询订单信息
        //将订单信息中的数据存到WxPayDTO
        WxPayDTO payDTO = new WxPayDTO();
        payDTO.setBody("商品描述");
        //订单总金额，单位为分
        payDTO.setTotalFee(1);
        //支付回调地址
        payDTO.setNotifyUrl(wxPayProperties.getNotifyUrl());
        payDTO.setOutTradeNo("商户订单号");
        //获取时间
        Date date = new Date();
        String timeStart = DateUtils.dateTimeNow();
        //结束时间设置在30分钟后
        String timeExpire = DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, DateUtils.getAfter(date, Calendar.MINUTE,30));
        //交易起始时间
        payDTO.setTimeStart(timeStart);
        //交易结束时间
        payDTO.setTimeExpire(timeExpire);
        //todo jsapi下单需要用户的openid
        payDTO.setOpenId("openid");
        Object url = payService.transactions(payDTO, WxPayConstants.TradeType.JSAPI);

        return url;
    }

    /**
     * 微信H5下单,返回跳转链接
     * @param orderNumber
     * @param request
     * @return
     */
    @GetMapping("/h5Pay")
    public Object h5Pay(@RequestParam("orderNumber") String orderNumber, HttpServletRequest request) {
        //todo 业务操作-根据订单编号查询订单信息

        //将订单信息中的数据存到WxPayDTO
        WxPayDTO payDTO = new WxPayDTO();
        payDTO.setBody("商品描述");
        //订单总金额，单位为分
        payDTO.setTotalFee(1);
        //支付回调地址
        payDTO.setNotifyUrl(wxPayProperties.getNotifyUrl());
        payDTO.setOutTradeNo("商户订单号");
        //获取时间
        Date date = new Date();
        String timeStart = DateUtils.dateTimeNow();
        //结束时间设置在30分钟后
        String timeExpire = DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, DateUtils.getAfter(date, Calendar.MINUTE,30));
        //交易起始时间
        payDTO.setTimeStart(timeStart);
        //交易结束时间
        payDTO.setTimeExpire(timeExpire);
        //todo H5下单需要用户的用户的客户端IP
        String remoteAddr = request.getRemoteAddr();
        payDTO.setPayerClientIp(remoteAddr);
        Object url = payService.transactions(payDTO, WxPayConstants.TradeType.JSAPI);

        return url;
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/wxCallback")
    public Object wxCallback(HttpServletRequest request, HttpServletResponse response) {
        ServletInputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            String notifyXml = StreamUtils.inputStream2String(inputStream, "utf-8");
            log.info(notifyXml);
            // 解析返回结果
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyXml);
            String jsonString = JSONObject.toJSONString(notifyMap);
            log.info(jsonString);
            // 判断支付是否成功
            if ("SUCCESS".equals(notifyMap.get("result_code"))) {
                //todo 修改订单状态

                // 支付成功：给微信发送我已接收通知的响应 创建响应对象
                Map<String, String> returnMap = new HashMap<>();
                returnMap.put("return_code", "SUCCESS");
                returnMap.put("return_msg", "OK");
                String returnXml = WXPayUtil.mapToXml(returnMap);
                response.setContentType("text/xml");
                log.info("支付成功");
                return returnXml;
            }else {
                //保存回调信息,方便排除问题
            }
            // 创建响应对象：微信接收到校验失败的结果后，会反复的调用当前回调函数
            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("return_code", "FAIL");
            returnMap.put("return_msg", "");
            String returnXml = WXPayUtil.mapToXml(returnMap);
            response.setContentType("text/xml");
            log.info("校验失败");
            return returnXml;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
