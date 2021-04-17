package com.pikacat.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.Admin;
import com.pikacat.blog.entity.SuperAdmin;
import com.pikacat.blog.service.AdminService;
import com.pikacat.blog.service.SuperAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/super-admin")
public class SuperAdminController {

    private final AdminService adminService;

    private final SuperAdminService superAdminService;

    public SuperAdminController(AdminService adminService, SuperAdminService superAdminService) {
        this.adminService = adminService;
        this.superAdminService = superAdminService;
    }

    // 检查admin重名
    @GetMapping("/duplicate-check/{username}")
    @ResponseBody
    public Boolean isDuplicateUsername(@PathVariable String username) {
        return adminService.superAdminIsDuplicateUsername(username);
    }

    @GetMapping("/get-all-admins")
    @ResponseBody
    public Map<String, Object> getAllAdmins(Long page, Short limit) {
        // 获取数据
        Page<Admin> adminPage = new Page<>(page, limit);
        adminService.superAdminGetAllAdmins(adminPage);
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的所有管理员");
        modelMap.put("data", adminPage.getRecords());
        modelMap.put("count", adminPage.getTotal());
        return modelMap;
    }

    @GetMapping("/delete-admin/{username}")
    public void deleteAdmin(@PathVariable String username) {
        adminService.superAdminDeleteAdmin(username);
    }

    @GetMapping("/edit-admin/{username}")
    public String editAdmin(@PathVariable String username) {
        return "super-edit-admin";
    }

    @PostMapping("/edit-admin/{oldUsername}")
    public String editAdmin(@PathVariable String oldUsername,
                            String newUsername,
                            String newPassword) {
        adminService.superAdminEditAdmin(newUsername, oldUsername, newPassword);
        return "redirect:/admin";
    }

    @GetMapping("/edit-superadmin/{username}")
    public String edit(@PathVariable String username) {
        return "super-edit-superadmin";
    }

    @PostMapping("/edit-superadmin/{username}")
    public String edit(String newUsername,
                       String newPassword,
                       SuperAdmin superAdmin) {
        superAdminService.superAdminEditSuperAdmin(newUsername, newPassword, superAdmin);
        return "redirect:/logout";
    }

    @GetMapping("/add-admin")
    public String addAdmin() {
        return "super-add-admin";
    }

    @PostMapping("/add-admin")
    public String addAdmin(Admin admin) {
        adminService.superadminAddAdmin(admin);
        return "redirect:/admin";
    }

}
