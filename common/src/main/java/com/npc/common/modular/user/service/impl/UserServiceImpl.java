package com.npc.common.modular.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.mapper.UserMapper;
import com.npc.common.modular.user.model.parame.UserParame;
import com.npc.common.modular.user.model.result.UserResult;
import com.npc.common.modular.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author wow
 * @createTime 2022/10/23 10:58
 * @descripttion 用户服务实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Override
    public UserResult findByAccount(String account) {
        return this.baseMapper.findByAccount(account);
    }
    @Override
    public void regist(UserParame user) {
        this.baseMapper.regist(user);
        return;
    }
    @Override
    public void delete(String id) {
        this.baseMapper.deleteById(id);
    }
}
