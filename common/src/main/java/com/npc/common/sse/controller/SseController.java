package com.npc.common.sse.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author NPC
 * @description
 * @create 2023/3/8 22:55
 */
@RestController
public class SseController {

    // 注入 SecurityManager
    @Autowired
    private DefaultSecurityManager securityManager;

    // 为客户端提供 SSE 事件流的控制器方法
    @CrossOrigin
    @GetMapping(path = "/messages", produces = "text/event-stream")
    public void getMessages(HttpServletResponse response) throws IOException {
        // 设置响应头，指定响应内容类型为 text/event-stream
        response.setContentType("text/event-stream;charset=utf-8");
        // 设置响应头，禁用浏览器的缓存
        response.setHeader("Cache-Control", "no-cache");

        // 模拟生成一些随机消息
        String[] messages = {"Hello, world!", "How are you doing?", "Welcome to SSE messaging!"};
        Random random = new Random();

        // 每 5 秒钟向客户端发送一条消息
        while (true) {
            // 从 messages 数组中随机选择一条消息
            String message = messages[random.nextInt(messages.length)];
            // 向客户端发送 SSE 事件
            response.getWriter().write("event: message\n");
            response.getWriter().write("data: " + message + "\n\n");
            response.getWriter().flush();

            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    // 保存 SseEmitter 对象的 Map
//    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    private Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    @GetMapping(path = "/messagessss", produces = "text/event-stream")
    public ResponseBodyEmitter openSSEConnection(HttpServletRequest request) {
        // 在异步方法中手动绑定 Shiro 的 SecurityManager
        SecurityUtils.setSecurityManager(securityManager); // 这里的 securityManager 需要替换为你实际的 SecurityManager 实例
        String userId = request.getParameter("userId");
        // 创建 SseEmitter 对象
        SseEmitter emitter = new SseEmitter();
        // 将 SseEmitter 对象保存到 Map 中
        emitterMap.put(userId, emitter);
        // 设置 SSE 响应的响应头和超时时间
        try {
            emitter.send(SseEmitter.event().name("connected").data("SSE connected").comment("Connected"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 结束之后的回调触发
        emitter.onCompletion(() -> emitterMap.remove(userId));
        // 超时回调 触发
        emitter.onTimeout(() -> emitter.complete());
        emitter.onError((throwable -> System.out.println(throwable)));

        // 返回 SseEmitter 对象
        return emitter;
    }
}
