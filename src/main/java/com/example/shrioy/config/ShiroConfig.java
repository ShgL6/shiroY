package com.example.shrioy.config;


import com.example.shrioy.filter.ShiroYAuthCFilter;
import com.example.shrioy.filter.ShiroYRoleFilter;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static final String INI_FILE_NAME = "shiro_config.ini";

    /**
     * sessionManager
     */
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);  // 禁用 URL 重写
        return sessionManager;
    }


    /**
     * SecurityManager
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("userRealm") Realm userRealm,
                                           @Qualifier("sessionManager") SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }


    /**
     *  配置过滤器 => 制定 URL 的访问权限
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加自定义的 filter
        LinkedHashMap<String, Filter> filterLinkedHashMap = new LinkedHashMap<>();
        filterLinkedHashMap.put("syauthc", new ShiroYAuthCFilter());
        filterLinkedHashMap.put("syrole",new ShiroYRoleFilter());
        shiroFilterFactoryBean.setFilters(filterLinkedHashMap);

        shiroFilterFactoryBean.setLoginUrl("/login");
        Map<String, String> map = new LinkedHashMap<>();

        // 有先后顺序
        map.put("/login", "anon");      // 允许匿名访问
        map.put("/", "anon");          // 允许匿名访问

        // 等效于本代码块下的语句
//        map.put("/logout", "syauthc,logout");   // 需要登录才能退出，默认重定向到 / 路径
//        map.put("/view", "syauthc,syrole[user,admin]");        // 需要登录,且指定角色才能访问（或）
//        map.put("/modify", "syauthc,syrole[admin]");        // 需要登录,且指定角色才能访问

        map.put("/logout", "logout");   // 需要登录才能退出，默认重定向到 / 路径
        map.put("/view", "syrole[user,admin]");        // 需要登录,且指定角色才能访问（或）
        map.put("/modify", "syrole[admin]");        // 需要登录,且指定角色才能访问
        map.put("/**", "syauthc");  // 需要登录才能访问

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * 开启 Shiro 注解模式 - AOP 支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
