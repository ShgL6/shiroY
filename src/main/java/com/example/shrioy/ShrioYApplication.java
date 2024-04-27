package com.example.shrioy;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@SpringBootApplication
public class ShrioYApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShrioYApplication.class, args);
    }



}
