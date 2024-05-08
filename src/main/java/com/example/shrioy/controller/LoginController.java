package com.example.shrioy.controller;

import com.example.shrioy.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    public static final String NEXT_PATH = "/view";

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    @ResponseBody
    public String index(){
        return "you are at index";
    }

    @GetMapping("/login")
    public String login(@RequestParam("u") String username,
                        @RequestParam("p") String pass){
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, pass);

        userSubject.login(token);

        return "redirect:" + NEXT_PATH;
    }

    /**
     * 测试基于过滤器的权限控制
     */
    @GetMapping("/view")
    @ResponseBody
    public String view(){
        return "you are viewing";
    }

    /**
     * 测试基于过滤器的权限控制
     */
    @GetMapping("/modify")
    @ResponseBody
    public String modify(){
        return "you are modifying";
    }

    /**
     * 测试基于注解的权限控制
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
                    returnSb.append(key)
                            .append(": ")
                            .append(session.getAttribute(key).toString())
                            .append("<br />");
                }
        );

        return returnSb.toString();
    }

    /**
     * 测试基于注解的权限控制 - 注解在 loginService.deepin() 上
     */
    @GetMapping("/deepin")
    @ResponseBody
    public String deepin(){
        return loginService.deepin();
    }


}
