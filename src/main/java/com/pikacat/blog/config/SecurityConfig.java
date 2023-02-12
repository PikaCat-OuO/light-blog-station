package com.pikacat.blog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置页面过滤规则
        http.authorizeRequests()
                // 配置超级管理员页面
                .antMatchers("/super-admin/**")
                .hasAuthority("SUPER_ADMIN")
                // 配置管理员页面
                .antMatchers("/admin/**")
                .hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                // 配置通用请求
                .antMatchers("/my-articles/edit-article/**", "/user-page/**/edit-user")
                .hasAnyAuthority("SUPER_ADMIN", "ADMIN", "USER")
                // 配置博客请求
                .antMatchers("/my-articles/**", "/article/add-comment/**", "/my-subscriptions/**")
                .hasAuthority("USER")
                // 其他请求一律通过
                .anyRequest()
                .permitAll();

        // 配置登录页面
        http.formLogin()
                // 添加登录功能
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .permitAll()
                // 添加记住我功能
                .and()
                .rememberMe()
                // 添加登出功能
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll();

        // 配置安全相关选项
        http
                // 防止iframe
                .headers()
                .frameOptions()
                .disable()
                // 关闭csrf
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 从数据库取数据验证
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}