package com.npc.common.modular.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.user.entity.UserRole;
import com.npc.common.modular.user.model.result.UserRoleResult;

import java.util.List;

public interface RoleService extends IService<UserRole> {
    List<UserRoleResult> findRoleByUserId(Integer id);
}
