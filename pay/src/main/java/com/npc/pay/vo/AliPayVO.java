package com.npc.pay.vo;

import lombok.Data;

/**
 * @author NPC
 * @description 支付宝支付请求对象 所需要的参数
 * @create 2025/2/17 14:58
 */
@Data
public class AliPayVO {
    private String out_trade_no; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String total_amount; // 付款金额 必填
    private String body; // 商品描述 可空
}
