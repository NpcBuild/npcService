package com.npc.common.login.third;

import com.npc.common.login.Login;
import com.npc.common.modular.user.entity.User;
import com.npc.core.ServerResponseVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author NPC
 * @description
 * @create 2024/3/11 22:49
 */
@Slf4j
public class PassportForThirdAdapter extends Login implements IPassportForThird{
    @Override
    public ServerResponseVO loginForQQ(String openId) {
        return doLogin(openId,LoginForQQAdapter.class);
    }

    @Override
    public ServerResponseVO loginForWeChat(String openId) {
        return doLogin(openId,LoginForWeChatAdapter.class);
    }

    @Override
    public ServerResponseVO<String> loginForRegist(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setUserPwd(password);
        HttpServletRequest request = null;
        super.login(user, request);
        return super.login(user, request);
    }

    // 使用简单工厂模式以及策略模式
    private ServerResponseVO doLogin(String openId,Class<? extends LoginAdapter> clazz) {
        try {
            LoginAdapter adapter = clazz.newInstance();
            if (adapter.support(adapter)) {
                return adapter.login(openId,adapter);
            }
        } catch (Exception e) {
            log.error("exception is",e);
        }
        return null;
    }
}
