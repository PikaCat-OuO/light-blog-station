package com.pikacat.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.Admin;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.entity.Category;
import com.pikacat.blog.entity.UserInfo;
import com.pikacat.blog.service.AdminService;
import com.pikacat.blog.service.ArticleInfoService;
import com.pikacat.blog.service.CategoryService;
import com.pikacat.blog.service.UserInfoService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserInfoService userInfoService;

    private final ArticleInfoService articleInfoService;

    private final CategoryService categoryService;

    private final AdminService adminService;

    public AdminController(UserInfoService userInfoService, ArticleInfoService articleInfoService,
                           CategoryService categoryService, AdminService adminService) {
        this.userInfoService = userInfoService;
        this.articleInfoService = articleInfoService;
        this.categoryService = categoryService;
        this.adminService = adminService;
    }

    @GetMapping("")
    public String admin() {
        return "admin";
    }

    // 检查管理员重名
    @GetMapping("/duplicate-check/{username}")
    @ResponseBody
    public Boolean isDuplicateUsername(@PathVariable String username) {
        return adminService.adminIsDuplicateUsername(username);
    }

    @GetMapping("/get-all-articles")
    @ResponseBody
    public Map<String, Object> getAllArticles(Long page, Short limit, @Nullable String field, @Nullable String order) {
        // 获取数据
        Page<ArticleInfo> articlesPage = new Page<>(page, limit);
        articleInfoService.adminGetAllArticles(articlesPage, field, order);
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的所有博客");
        modelMap.put("count", articlesPage.getTotal());
        modelMap.put("data", articlesPage.getRecords());
        return modelMap;
    }

    @GetMapping("/delete-article/{articleUid}")
    @ResponseBody
    public void deleteArticle(@PathVariable Long articleUid) {
        articleInfoService.adminDeleteArticleByArticleUid(articleUid);
    }

    @GetMapping("/get-all-users")
    @ResponseBody
    public Map<String, Object> getAllUsers(Long page, Short limit, @Nullable String field, @Nullable String order) {
        // 获取数据
        Page<UserInfo> userInfosPage = new Page<>(page, limit);
        userInfoService.adminGetAllUserInfo(userInfosPage, field, order);
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的所有用户");
        modelMap.put("count", userInfosPage.getTotal());
        modelMap.put("data", userInfosPage.getRecords());
        return modelMap;
    }

    @GetMapping("/delete-user/{username}")
    @ResponseBody
    public void deleteUserByUsername(@PathVariable String username) {
        userInfoService.adminDeleteUserByUsername(username);
    }

    @GetMapping("/get-all-category")
    @ResponseBody
    public Map<String, Object> getAllCategory(Long page, Short limit) {
        // 获取数据
        Page<Category> categoryPage = new Page<>(page, limit);
        categoryService.adminGetAllCategory(categoryPage);
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的所有分类");
        modelMap.put("data", categoryPage.getRecords());
        modelMap.put("count", categoryPage.getTotal());
        return modelMap;
    }

    @GetMapping("/delete-category/{category}")
    @ResponseBody
    public void deleteCategory(@PathVariable String category) {
        categoryService.adminDeleteCategory(category);
    }

    @GetMapping("/edit-category/{category}")
    public String editCategory(@PathVariable String category) {
        return "edit-category";
    }

    @PostMapping("/edit-category/{oldCategory}")
    public String editCategory(@PathVariable String oldCategory,
                             String newCategory) {
        categoryService.adminEditCategory(newCategory, oldCategory);
        return "redirect:/admin";
    }

    // 管理员编辑管理员信息获取页面
    @GetMapping("/edit-admin/{username}")
    public String editAdmin(@PathVariable String username) {
        return "edit-admin";
    }

    // 管理员编辑管理员信息提交
    @PostMapping("/edit-admin/{username}")
    public String editAdmin(String newUsername,
                          String newPassword,
                          Admin admin) {
        adminService.adminEditAdmin(newUsername, newPassword, admin);
        return "redirect:/logout";
    }

}
