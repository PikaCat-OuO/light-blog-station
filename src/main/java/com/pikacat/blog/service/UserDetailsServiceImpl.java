package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pikacat.blog.entity.Admin;
import com.pikacat.blog.entity.SuperAdmin;
import com.pikacat.blog.entity.UserInfo;
import com.pikacat.blog.mapper.AdminMapper;
import com.pikacat.blog.mapper.SuperAdminMapper;
import com.pikacat.blog.mapper.UserInfoMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


// 与springsecurity登录有关
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SuperAdminMapper superAdminMapper;

    private final AdminMapper adminMapper;

    private final UserInfoMapper userInfoMapper;

    public UserDetailsServiceImpl(SuperAdminMapper superAdminMapper, AdminMapper adminMapper, UserInfoMapper userInfoMapper) {
        this.superAdminMapper = superAdminMapper;
        this.adminMapper = adminMapper;
        this.userInfoMapper = userInfoMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 超级管理员验证
        SuperAdmin superAdmin = superAdminMapper.selectOne(new QueryWrapper<SuperAdmin>()
                .eq("username", username)
        );
        if (superAdmin != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
            return new User(superAdmin.getUsername(), superAdmin.getPassword(), authorities);
        }
        // 管理员验证
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", username)
        );

        if (admin != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            return new User(admin.getUsername(), admin.getPassword(), authorities);
        }

        // 博客用户验证
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("username", username)
        );
        if (userInfo != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));
            return new User(userInfo.getUsername(), userInfo.getPassword(), authorities);
        }

        // 所有验证都不通过
        throw new UsernameNotFoundException("找不到用户名");

    }
}
