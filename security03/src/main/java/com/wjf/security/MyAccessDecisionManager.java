package com.wjf.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 判断当前登录的用户是否具备当前请求URL所需要的角色信息，
     * 如果不具备，就抛出AccessDeniedException异常，否则不做任何事
     * @param authentication 包含当前登录用户的信息；
     * @param object FilterInvocation对象，可以获取当前请求对象等
     * @param configAttributes FilterInvocationSecurityMetadataSource中的个getAttributes方法的返回值，即当前请求URL所需要的角色
     * @throws AccessDeniedException 访问拒绝异常
     * @throws InsufficientAuthenticationException 身份验证不足异常
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute:configAttributes){
            if ("ROLE_login".equals(configAttribute.getAttribute()) && authentication instanceof UsernamePasswordAuthenticationToken){
                return;
            }
            for (GrantedAuthority authority:authorities){
                if (configAttribute.getAttribute().equals(authority.getAuthority())){
                    return;
                }
            }

            throw new AccessDeniedException("权限不足");

        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
