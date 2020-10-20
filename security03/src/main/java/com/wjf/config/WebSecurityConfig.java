package com.wjf.config;

import com.wjf.security.MyAccessDecisionManager;
import com.wjf.security.MyFilterInvocationSecurityMetadataSource;
import com.wjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userService")
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource());
                        object.setAccessDecisionManager(myAccessDecisionManager());
                        return object;
                    }
                })
                .and()
                .formLogin()
                .loginProcessingUrl("/login").successForwardUrl("/admin/hello").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource(){
        return new MyFilterInvocationSecurityMetadataSource();
    }

    @Bean
    MyAccessDecisionManager myAccessDecisionManager(){
        return new MyAccessDecisionManager();
    }


}
