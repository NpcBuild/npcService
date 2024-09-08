package com.npc.common.login.third;

import com.npc.core.ServerResponseVO;

/**
 * 标准的适配接口
 */
public interface LoginAdapter {
    boolean support(Object adapter);
    ServerResponseVO login(String id,Object adapter);
}
