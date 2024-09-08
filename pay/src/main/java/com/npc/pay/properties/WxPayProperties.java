package com.npc.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description 微信支付属性类
 * @create 2024/6/22 14:41
 */
@ConfigurationProperties(prefix = "wx.pay")
@Data
@Component
@Primary
public class WxPayProperties {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    private String secret;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 回调地址
     */
    private String notifyUrl;
}