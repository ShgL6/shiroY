package com.example.shrioy.controller;

import com.example.shrioy.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    public static final String NEXT_PATH = "/deepin";

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("pass") String pass){
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, pass);

        userSubject.login(token);

        return "redirect:" + NEXT_PATH;
    }

    /**
     * 测试基于注解的权限控制
     * @return
     */
    @GetMapping("/search")
    @RequiresRoles(value = {"admin"})
    @ResponseBody
    public String search(){

        StringBuilder returnSb = new StringBuilder("you are searching");
        returnSb.append("<br />");
        Session session = SecurityUtils.getSubject().getSession();

        session.getAttributeKeys().forEach(
                key -> {
                    returnSb.append(key + ": " + session.getAttribute(key).toString());
                    returnSb.append("<br />");
                }
        );

        return returnSb.toString();
    }

    @GetMapping("/deepin")
    @ResponseBody
    public String deepin(){
        return loginService.deepin();
    }


}
