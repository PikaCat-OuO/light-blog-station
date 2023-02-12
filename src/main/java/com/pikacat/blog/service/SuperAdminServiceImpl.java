package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pikacat.blog.entity.SuperAdmin;
import com.pikacat.blog.mapper.SuperAdminMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminMapper superAdminMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SuperAdminServiceImpl(SuperAdminMapper superAdminMapper,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.superAdminMapper = superAdminMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void superAdminEditSuperAdmin(String newUsername, String newPassword, SuperAdmin superAdmin) {
        // 检查密码是否正确
        SuperAdmin originSuperAdmin = superAdminMapper.selectOne(new QueryWrapper<SuperAdmin>()
                .eq("username", superAdmin.getUsername())
        );
        if (!bCryptPasswordEncoder.matches(superAdmin.getPassword(), originSuperAdmin.getPassword())) {
            return;
        }
        if (!newUsername.equals(superAdmin.getUsername())) {
            // 改名了
            superAdminMapper.updateUsername(newUsername, superAdmin.getUsername());
            superAdmin.setUsername(newUsername);
        }
        // 数据长度校验
        if (newUsername.length() < 1 || newUsername.length() > 20 ||
                !"".equals(newPassword) && (newPassword.length() < 8 || newPassword.length() > 20)) {
            return;
        }
        if (!"".equals(newPassword)) {
            // 改密码了
            superAdmin.setPassword(bCryptPasswordEncoder.encode(newPassword));
            superAdminMapper.updateById(superAdmin);
        }
    }
}
