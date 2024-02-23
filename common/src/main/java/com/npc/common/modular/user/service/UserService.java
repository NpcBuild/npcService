package com.npc.common.modular.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.model.parame.UserParame;
import com.npc.common.modular.user.model.result.UserResult;

public interface UserService extends IService<User> {
    UserResult findByAccount(String account);
    void regist(UserParame user);
    void delete(String id);
}
