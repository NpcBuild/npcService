package com.npc.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author NPC
 * @description
 * @create 2024/6/22 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WxPayDTO {
    /**
     * openId
     */
    private String openId;


    /**
     * 字段名：商品描述.
     *   变量名：body
     *   是否必填：是
     *   类型：String(128)
     *   示例值： 开发喵会员充值
     *   描述：商品简单描述，该字段须严格按照规范传递，具体请见参数规定
     */
    private String body;

    /**
     * 字段名：总金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值： 543
     * 描述：订单总金额，单位为分，详见支付金额
     */
    private Integer totalFee;

    /**
     * 字段名：通知地址.
     * 变量名：notify_url
     * 是否必填：是
     * 类型：String(256)
     * 示例值：http://www.weixin.qq.com/wxpay/pay.php
     * 描述：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     */
    private String notifyUrl;

    /**
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：20230806125346
     * 描述：商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
     */
    private String outTradeNo;

    /**
     * 字段名：交易起始时间.
     * 变量名：time_start
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20231225091010
     * 描述：订单生成时间，格式为yyyyMMddHHmmss，如2023年12月25日9点10分10秒表示为20231225091010。其他详见时间规则
     */
    private String timeStart;

    /**
     * 字段名：交易结束时间.
     * 变量名：time_expire
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20231227091010
     * 描述：订单失效时间，格式为yyyyMMddHHmmss，如2023年12月27日9点10分10秒表示为20231227091010。其他详见时间规则
     *   注意：最短失效时间间隔必须大于5分钟
     */
    private String timeExpire;

    /**
     * 字段名：用户的客户端IP
     * 变量名：payer_client_ip
     * 是否必填：是
     * 类型：String(14)
     * 示例值：14.23.150.211
     * 描述：用户的客户端IP，支持IPv4和IPv6两种格式的IP地址。
     *   注意：最短失效时间间隔必须大于5分钟
     */
    private String payerClientIp;
}
