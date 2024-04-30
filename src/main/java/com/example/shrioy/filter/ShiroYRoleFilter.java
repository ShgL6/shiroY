package com.example.shrioy.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

public class ShiroYRoleFilter extends AccessControlFilter {

    /**
     * 判断访问是否被允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        boolean isAccessAllowed = false;

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        List<String> roles = CollectionUtils.asList(rolesArray);
        System.out.println("========= need at least one role in roles =========");
        roles.forEach(System.out::println);

        for (boolean b : subject.hasRoles(roles)) {
            isAccessAllowed = isAccessAllowed || b;
        }
        return isAccessAllowed;
    }

    /**
     * 处理非法访问
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws  IOException {


        /*
         * note:
         * 这种直接抛出异常的方式是错误的，因为在 @ControllerAdvice 只会捕获 @Controller 中调用方法抛出的异常。
         * 而过滤器中的异常则是早于 请求到达 @Controller 被抛出的，所以此异常不会被 spring 捕获
         * 正确的做法是 response 返回错误信息
         */
        // 直接抛出异常，全局统一处理 [×]
        // throw new AuthorizationException();

        response.getWriter().println("You have no authorization to that !!!");
        return false;
    }
}
