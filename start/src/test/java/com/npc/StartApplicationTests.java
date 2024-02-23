package com.npc;

import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.mapper.UserMapper;
import com.npc.common.modular.user.mapper.UserRoleMapper;
import com.npc.common.modular.user.model.result.UserResult;
import com.npc.common.modular.user.model.result.UserRoleResult;
import com.npc.common.modular.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.List;

//@SpringBootTest
class StartApplicationTests {
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper roleMapper;
    @Test
    void contextLoads() {
//        int user2 = userService.find();
//        User user2 = userMapper.findByAccount("yf");
//        User user = userService.getById(1);
        User user2 = userService.getById(1);
//        List<UserRoleResult> s = roleMapper.findRoleByUserId(1);
        System.out.println(user2);
//        System.out.println(s);
//        System.out.println(user);
    }
    @Test
    void test() {
        System.out.println("");
    }

}
