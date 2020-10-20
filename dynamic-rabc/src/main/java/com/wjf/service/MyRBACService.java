package com.wjf.service;

import com.wjf.mapper.RBACMapper;
import com.wjf.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class MyRBACService {

    @Autowired
    private AntPathMatcher antPathMatcher;
    @Autowired
    private RBACMapper rbacMapper;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication){
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails){

            String username = ((UserDetails) principal).getUsername();

            List<String> uris = rbacMapper.findURISByUsername(username);
            log.info("[{}]",uris);
                return uris.stream().anyMatch(url -> antPathMatcher.match(url,request.getRequestURI()));
            }
        log.info("权限不匹配");
        return false;
    }
}
