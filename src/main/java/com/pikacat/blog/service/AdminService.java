package com.pikacat.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.Admin;

public interface AdminService {

    // 超级管理员检查重名问题
    Boolean superAdminIsDuplicateUsername(String username);

    // 超级管理员获取管理员信息
    void superAdminGetAllAdmins(Page<Admin> adminsPage);

    // 超级管理员删除管理员
    void superAdminDeleteAdmin(String username);

    // 超级管理员修改管理员信息
    void superAdminEditAdmin(String newUsername, String oldUsername, String newPassword);

    // 管理员修改信息
    void adminEditAdmin(String newUsername, String newPassword, Admin admin);

    // 管理员检查重名
    Boolean adminIsDuplicateUsername(String username);

    // 超级管理员新增管理员
    void superadminAddAdmin(Admin admin);

}
