package com.npc.client.Netty.service;

import org.apache.poi.ss.formula.functions.T;

public interface PushMsgService {
    /**
     * 推送给指定用户
     */
    void pushMsgToOne(String userId, Object msg);

    /**
     * 推送给所有用户
     */
    void pushMsgToAll(String msg);
}
