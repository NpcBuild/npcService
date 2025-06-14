package com.npc.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/wx/push")
public class PushController {

    @Resource
    private WxMpService wxMpService;

    @PostMapping("/template")
    public String pushTemplateMsg(@RequestParam String openId) {
        // 模拟一个模板消息推送
        try {
            // 可用 templateId: 需在公众号后台配置模板
            // wxMpService.getTemplateMsgService().sendTemplateMsg(...);
            return "模拟发送成功（功能待扩展）";
        } catch (Exception e) {
            log.error("发送模板消息失败", e);
            return "发送失败：" + e.getMessage();
        }
    }
}

