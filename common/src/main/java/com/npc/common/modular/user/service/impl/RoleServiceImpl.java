package com.npc.common.modular.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.user.entity.UserRole;
import com.npc.common.modular.user.mapper.UserRoleMapper;
import com.npc.common.modular.user.model.result.UserRoleResult;
import com.npc.common.modular.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author wow
 * @createTime 2022/10/23 10:58
 * @descripttion 用户角色服务实现
 */
@Service
public class RoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements RoleService {

    @Resource
    private UserRoleMapper roleMapper;

    @Override
    public List<UserRoleResult> findRoleByUserId(Integer id) {
        return roleMapper.findRoleByUserId(id);
    }
}
