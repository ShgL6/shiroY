package com.example.shrioy.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    /**
     * 测试基于注解的细粒度权限控制
     * @return
     */
    @RequiresPermissions(value = {"deepin"})
    public String deepin(){
        return "you deepin the service method";
    }

}
