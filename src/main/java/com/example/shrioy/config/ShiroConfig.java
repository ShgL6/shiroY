package com.example.shrioy.config;


import com.example.shrioy.filter.ShiroYRoleFilter;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
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
     * SecurityManager
     */
    @Bean("securityManager")
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }


    /**
     * Realm 加载用户的认证和权限信息
     */
    @Bean
    public Realm realm(){
        String path = this.getClass().getClassLoader().getResource(INI_FILE_NAME).getPath();
        IniRealm iniRealm = new IniRealm(path);
        return iniRealm;
    }
    



    /**
     *  配置过滤器 => 制定 URL 的访问权限
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加自定义的 filter
        LinkedHashMap<String, Filter> filterLinkedHashMap = new LinkedHashMap<>();
        filterLinkedHashMap.put("syrole",new ShiroYRoleFilter());
        shiroFilterFactoryBean.setFilters(filterLinkedHashMap);

        shiroFilterFactoryBean.setLoginUrl("/login");
        Map<String, String> map = new LinkedHashMap<>();

        // 有先后顺序
        map.put("/login", "anon");      // 允许匿名访问
        map.put("/fail", "anon");      // 允许匿名访问
        map.put("/", "anon");          // 允许匿名访问
        map.put("/logout", "logout");   // 退出，默认重定向到 / 路径
        map.put("/view", "syrole[user,admin]");        // 指定角色才能访问（或）
        map.put("/modify", "syrole[admin]");        // 指定角色才能访问

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }


    /**
     * 开启 Shiro 注解模式
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
