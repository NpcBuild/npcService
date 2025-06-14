package com.npc.wechat.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Service;

@Service
public class AutoReplyService {

    public WxMpXmlOutMessage reply(WxMpXmlMessage inMessage) {
        String content = inMessage.getContent();
        String reply;

        if ("你好".equals(content)) {
            reply = "你好啊，有什么可以帮你？";
        } else if ("帮助".equals(content)) {
            reply = "你可以输入关键词如：菜单、功能、联系等";
        } else {
            reply = "我听不懂你说的：" + content;
        }

        return WxMpXmlOutMessage.TEXT()
                .content(reply)
                .fromUser(inMessage.getToUser())
                .toUser(inMessage.getFromUser())
                .build();
    }
}
