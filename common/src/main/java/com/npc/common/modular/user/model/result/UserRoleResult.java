package com.npc.common.modular.user.model.result;

import lombok.Data;

/**
 * @Author wow
 * @createTime 2022/10/23 11:17
 */
@Data
public class UserRoleResult {
    private Integer id;
    private String userId;
    private String roleId;

    private String role;

    private String desc;
}
