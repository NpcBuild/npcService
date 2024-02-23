package com.npc.common.Shiro;

import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.model.result.UserResult;
import com.npc.common.modular.user.model.result.UserRoleResult;
import com.npc.common.modular.user.service.PermissionService;
import com.npc.common.modular.user.service.RoleService;
import com.npc.common.modular.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 负责认证用户身份和对用户进行授权
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    //用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<UserRoleResult> roleList = roleService.findRoleByUserId(user.getId());
        Set<String> roleSet = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
        for (UserRoleResult role : roleList){
            roleSet.add(role.getRole());
            roleIds.add(role.getId());
        }
        //放入角色信息
        authorizationInfo.setRoles(roleSet);
        //放入权限信息
        List<String> permissionList = permissionService.findByRoleId(roleIds);
        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));

        return authorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException{
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        UserResult user = userService.findByAccount(token.getUsername());
        if (user == null){
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getUserPwd(), getName());
    }
}
