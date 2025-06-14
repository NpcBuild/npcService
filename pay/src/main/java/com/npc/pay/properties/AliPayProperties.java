package com.npc.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author NPC
 * @description 支付宝支付属性类
 * @create 2024/6/22 14:41
 */
@ConfigurationProperties(prefix = "alipay.pay")
@Data
@Component
@Primary
public class AliPayProperties {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 应用私钥
     */
    private String appPrivateKey;

    /**
     * 支付宝支付公钥
     */
    private String alipayPublicKey;

    /**
     * 回调地址
     */
    private String notifyUrl;

}