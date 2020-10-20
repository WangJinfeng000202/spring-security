package com.wjf.springdemo.config;

import com.wjf.springdemo.Service.UserDetailServiceImpl;
import com.wjf.springdemo.handler.MyAccessDeniedHandler;
import com.wjf.springdemo.handler.MyAuthenticationFailureHandler;
import com.wjf.springdemo.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //开启表单登录
                .formLogin().loginPage("/login.html")
                //自定义参数
                .usernameParameter("username1")
                .passwordParameter("password1")
                //登录处理接口
                .loginProcessingUrl("/login")
                //登录成功处理接口
                .successForwardUrl("/toMain")
                //自定义登录成功处理器
//                .successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                //登录是失败处理接口
//                .failureForwardUrl("/toError")
                .failureHandler(new MyAuthenticationFailureHandler("/error.html"))
                .and()
                .authorizeRequests()
                //放行
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
                .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
//                .antMatchers("/**/*.jpg").permitAll()
//                .regexMatchers(".+[.].png").permitAll()
//                .regexMatchers(HttpMethod.POST,"/demo").permitAll()
//                .mvcMatchers("demo").servletPath("/xxxx").permitAll()
//                .antMatchers("/xxxxx/demo").permitAll()
//                .antMatchers("/ah.html").hasAuthority("admin")
//                .antMatchers("/ah.html").hasAnyAuthority("admin","user")
//                .antMatchers("/ah.html").hasRole("abc")
//                .antMatchers("/ah.html").hasAnyRole("adc","abC");
//                .antMatchers("/ah.html").access("hasRole('admin')");

//                基于ip地址的访问
//                .antMatchers("/ah.html").hasIpAddress("127.0.0.1")

//                .anyRequest().authenticated()
                //自定义access方法
                .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)")
                .and()

                //remember-me
//                .rememberMe()
//                .tokenRepository(persistentTokenRepository)
                //.rememberMeParameter()
                //60s过期默认两周
//                .tokenValiditySeconds(60)
//                .userDetailsService(userDetailService)
//                .and()

                //自定义403
//                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler)
//
//
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login.html")
//                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表、第一次启动建表，后注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }

}
