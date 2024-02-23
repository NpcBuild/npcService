package com.npc.common.modular.user.entity;


import lombok.Data;

/**
 * @Author wow
 * @createTime 2022/9/20 21:59
 * @descripttion 用户角色表
 */
@Data
public class UserRole {
    private Integer id;
    private String userId;
    private String roleId;
}
