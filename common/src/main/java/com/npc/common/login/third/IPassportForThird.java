package com.npc.common.login.third;

import com.npc.core.ServerResponseVO;

/**
 * 对外接口
 */
public interface IPassportForThird {
    ServerResponseVO loginForQQ(String openId);
    ServerResponseVO loginForWeChat(String openId);
    ServerResponseVO<String> loginForRegist(String userName, String password);
}
