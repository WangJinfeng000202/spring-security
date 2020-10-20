package com.wjf.security;

import com.wjf.entity.Menu;
import com.wjf.entity.Role;
import com.wjf.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    //实现ant风格的URL匹配
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    MenuMapper menuMapper;

    /**
     * 主要方法
     * @param object FilerInvocation 可以从中提取当前请求的URL
     * @return 当前请求URL所需的角色
     * @throws IllegalArgumentException 非法参数异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //参数中提取当前请求的URL
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        //从数据库中提取所有的资源信息 menu以及对应的role，真实开发中可以存入Redis缓存中或其他缓存数据库
        List<Menu> allMenu = menuMapper.getAllMenu();

        for (Menu menu:allMenu){
            if(antPathMatcher.match(menu.getPattern(),requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] roleArr = new String[roles.size()];
                for (int i = 0; i < roleArr.length; i++) {
                    roleArr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(roleArr);
            }
        }

        //如果请求的资源中不存在相应的模式，则假设请求登录后即可访问，即直接返回ROLE_LOGIN
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    /**
     * 返回所有定义好的权限资源，
     * Spring Security在启动时会校验相关配置是否正确，
     * 如果不需要校验，那么该方法直接返回null即可
     * @return 不校验配置
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 方法返回类对象是否支持校验
     * @param clazz 类对象
     * @return 支持校验
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
