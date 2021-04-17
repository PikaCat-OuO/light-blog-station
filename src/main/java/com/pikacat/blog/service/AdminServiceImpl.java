package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.Admin;
import com.pikacat.blog.mapper.AdminMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminServiceImpl(AdminMapper adminMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminMapper = adminMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Boolean superAdminIsDuplicateUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", username)
        ) != null;
    }

    public void superAdminGetAllAdmins(Page<Admin> adminsPage) {
        adminMapper.selectPage(adminsPage, new QueryWrapper<>());
    }

    @Override
    public void superAdminDeleteAdmin(String username) {
        adminMapper.delete(new QueryWrapper<Admin>()
                .eq("username", username)
        );
    }

    @Override
    public void superAdminEditAdmin(String newUsername, String oldUsername, String newPassword) {
        // 如果用户名变了
        if (!newUsername.equals(oldUsername)) {
            adminMapper.updateUsername(newUsername, oldUsername);
        }
        if (!"".equals(newPassword)) {
            // 设置了新的密码
            Admin admin = new Admin(newUsername, bCryptPasswordEncoder.encode(newPassword));
            adminMapper.updateById(admin);
        }
    }

    @Override
    public void adminEditAdmin(String newUsername, String newPassword, Admin admin) {
        // 检查密码是否正确
        Admin originAdmin = adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", admin.getUsername())
        );
        if (!bCryptPasswordEncoder.matches(admin.getPassword(), originAdmin.getPassword())) {
            return;
        }
        // 数据长度校验
        if (newUsername.length() < 1 || newUsername.length() > 20 ||
                !"".equals(newPassword) && (newPassword.length() < 8 || newPassword.length() > 20)) {
            return;
        }
        if (!newUsername.equals(admin.getUsername())) {
            adminMapper.updateUsername(newUsername, admin.getUsername());
            admin.setUsername(newUsername);
        }
        if (!"".equals(newPassword)) {
            // 设置了新密码，用BCrypt加密
            admin.setPassword(bCryptPasswordEncoder.encode(newPassword));
            adminMapper.updateById(admin);
        }
    }

    @Override
    public Boolean adminIsDuplicateUsername(String username) {
        return superAdminIsDuplicateUsername(username);
    }

    @Override
    public void superadminAddAdmin(Admin admin) {
        // 检查参数正确性
        if (admin.getUsername().length() >= 1 && admin.getUsername().length() <= 20 &&
                admin.getPassword().length() >= 8 && admin.getPassword().length() <= 20) {
            // 修正密码
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
            adminMapper.insert(admin);
        }
    }

}
