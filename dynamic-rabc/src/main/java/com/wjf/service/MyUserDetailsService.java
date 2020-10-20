package com.wjf.service;

import com.wjf.mapper.RBACMapper;
import com.wjf.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private RBACMapper rbacMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails user = rbacMapper.findByUsername(username);
        List<String> roles = rbacMapper.findRolesByUsername(username);
        List<String> authorities = rbacMapper.findAuthoritiesByRoles(roles);
        roles = roles.stream().map(r -> "ROLE_" + r).collect(Collectors.toList());

        authorities.addAll(roles);

        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", authorities)));

        return user;
    }
}
