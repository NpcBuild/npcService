package com.npc.common.modular.user.service;

import java.util.List;

public interface PermissionService {
    List<String> findByRoleId(List<Integer> roleIds);
}
