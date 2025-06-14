package com.npc.wechat.controller;

import com.npc.wechat.service.AutoReplyService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/wx/mp")
public class WechatController {

    @Resource
    private WxMpService wxMpService;
    @Resource
    private AutoReplyService autoReplyService;

    @GetMapping("/callback")
    public String verify(
            @RequestParam String signature,
            @RequestParam String timestamp,
            @RequestParam String nonce,
            @RequestParam String echostr) {

        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "illegal request";
    }

    @PostMapping("/callback")
    public String handleMessage(@RequestBody String requestBody,
                                @RequestParam String signature,
                                @RequestParam String timestamp,
                                @RequestParam String nonce,
                                @RequestParam(required = false) String encType,
                                @RequestParam(required = false) String msgSignature) {

        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
        log.info("收到消息：{}", inMessage);

        WxMpXmlOutMessage outMessage = autoReplyService.reply(inMessage);


        return outMessage.toXml();
    }
}

