package com.example.shrioy.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ShiroYExceptionHandler {

    @ExceptionHandler(value = {AuthorizationException.class, AuthenticationException.class})
    @ResponseBody
    public String handleException(Throwable e){
        System.out.println("=============== blocked ================");
        if(e instanceof AuthenticationException){
            return "You haven't pass the authentication !!!";
        }
        else if(e instanceof AuthorizationException){
            return "You have no authorization to that !!!";
        }
        return "Unknown Error !!!";

    }

}
