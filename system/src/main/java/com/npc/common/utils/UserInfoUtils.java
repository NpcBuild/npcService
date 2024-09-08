package com.npc.common.utils;

import com.npc.common.dto.UserInfos;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author NPC
 * @description
 * @create 2024/5/8 8:59
 */
// todo: FIX获取当前登录人异常问题
public class UserInfoUtils {
    public static UserInfos get() {
        // 获取当前用户的 Subject
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated()) {
                return (UserInfos) currentUser.getPrincipal();
            }
            UserInfos userInfos = new UserInfos();
            userInfos.setId(1);
            return userInfos;
        } catch (Exception e) {
            UserInfos userInfos = new UserInfos();
            userInfos.setId(1);
            return userInfos;
        }
    }
}
