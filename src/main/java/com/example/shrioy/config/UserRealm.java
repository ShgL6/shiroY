package com.example.shrioy.config;

import com.example.shrioy.entity.*;
import com.example.shrioy.mapper.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userRealm")
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRolesMapper userRolesMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionsMapper rolePermissionsMapper;

    /**
     * 提供认证所需要的信息：如用户名，密码，加密盐等
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userMapper.selectByUsername(username);
        if(user == null){
            throw new AuthenticationException();
        }
        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
    }


    /**
     * 提供授权时需要的信息：比如角色，权限等
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userMapper.selectByUsername(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<Integer> roleIds = userRolesMapper.selectByUserId(user.getUserId())
                .stream().map(UserRoles::getRoleId).toList();

        List<String> roleList = roleMapper.selectByRoleId(roleIds)
                .stream().map(Role::getRoleName).toList();

        List<Integer> permissionIds = rolePermissionsMapper.getRolePermissionsByRoleId(roleIds)
                .stream().map(RolePermissions::getPermissionId).toList();

        List<String> permissionList = permissionMapper.selectByPermissionId(permissionIds)
                .stream().map(Permission::getPermissionName).toList();

        info.addRoles(roleList);    // 添加角色
        info.addStringPermissions(permissionList);  // 添加权限

        return info;
    }


}
