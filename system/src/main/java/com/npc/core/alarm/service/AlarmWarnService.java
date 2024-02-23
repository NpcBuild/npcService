package com.npc.core.alarm.service;

import com.npc.core.alarm.template.NotifyMessage;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 15:30
 */
public interface AlarmWarnService {

    /**
     * 发送信息
     *
     * @param notifyMessage message
     */
    void send(NotifyMessage notifyMessage);
}
