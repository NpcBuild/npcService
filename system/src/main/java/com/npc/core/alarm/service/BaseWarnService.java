package com.npc.core.alarm.service;

import com.npc.core.alarm.MessageTypes;
import com.npc.core.alarm.template.NotifyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 15:31
 */
@Slf4j
public abstract class BaseWarnService implements AlarmWarnService {

    @Override
    public void send(NotifyMessage notifyMessage) {
        if (notifyMessage.getMessageTye().equals(MessageTypes.TEXT)) {
            CompletableFuture.runAsync(() -> {
                try {
                    doSendText(notifyMessage.getMessage());
                } catch (Exception e) {
                    log.error("send text warn message error", e);
                }
            });
        } else if (notifyMessage.getMessageTye().equals(MessageTypes.MD)) {
            CompletableFuture.runAsync(() -> {
                try {
                    doSendMarkdown(notifyMessage.getTitle(), notifyMessage.getMessage());
                } catch (Exception e) {
                    log.error("send markdown warn message error", e);
                }
            });
        }
    }

    /**
     * 发送Markdown消息
     *
     * @param title   Markdown标题
     * @param message Markdown消息
     * @throws Exception 异常
     */
    protected abstract void doSendMarkdown(String title, String message) throws Exception;

    /**
     * 发送文本消息
     *
     * @param message 文本消息
     * @throws Exception 异常
     */
    protected abstract void doSendText(String message) throws Exception;
}
