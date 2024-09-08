package com.npc.common.login.third;

import com.npc.core.ServerResponseVO;

/**
 * @author NPC
 * @description
 * @create 2024/3/11 22:45
 */
public class LoginForQQAdapter implements LoginAdapter{

    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public ServerResponseVO login(String id, Object adapter) {
        return null;
    }
}
