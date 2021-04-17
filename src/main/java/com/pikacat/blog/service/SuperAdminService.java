package com.pikacat.blog.service;

import com.pikacat.blog.entity.SuperAdmin;

public interface SuperAdminService {

    // 修改超级管理员的信息
    public void superAdminEditSuperAdmin(String newUsername, String newPassword, SuperAdmin superAdmin);

}
