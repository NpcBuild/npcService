package com.npc.client.Netty.controller;

import com.npc.client.Netty.service.PushMsgService;
import com.npc.common.modular.message.entity.Message;
import com.npc.common.modular.message.service.IMessageService;
import com.npc.common.modular.user.model.result.UserResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wow
 * @createTime 2022/11/5 23:34
 * @descripttion
 */
@CrossOrigin
@RestController
@RequestMapping("/socket")
public class IndexController {
    @Autowired
    PushMsgService pushMsgService;
    @Autowired
    IMessageService messageService;

    @GetMapping("")
    public ModelAndView index(){
        ModelAndView mav=new ModelAndView("socket");
        mav.addObject("uid", RandomUtils.nextInt());
        return mav;
    }

    @GetMapping("/{uid}")
    public void pushOne(HttpServletRequest request, @PathVariable String uid) {
        // 获取当前用户的 Subject
        Subject currentUser = SecurityUtils.getSubject();
        UserResult userInfo = new UserResult();
        if (currentUser.isAuthenticated()) {
            userInfo = (UserResult)currentUser.getPrincipal();
            System.out.println(userInfo.getId());
            // 在这里可以继续处理用户信息
        } else {
            // 用户未认证或未登录的情况处理
        }
        // 从请求参数中获取用户 ID
        String messages = request.getParameter("message");
        pushMsgService.pushMsgToOne(uid, messages);

        Message message = new Message();
        message.setText(messages);
        message.setType("text");
        message.setReceiverId(Integer.valueOf(uid));
        message.setSenderId(userInfo.getId());
        message.setTimestamp(System.currentTimeMillis());
        messageService.save(message);
    }
}
