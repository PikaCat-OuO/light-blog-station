package com.pikacat.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.service.ArticleInfoService;
import com.pikacat.blog.service.UserInfoService;
import com.pikacat.blog.util.SecurityUtil;
import com.pikacat.blog.vo.UserInfoVo;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// 个人页面的控制器
@Controller
@RequestMapping("/user-page/{username}")
public class UserPageController {

    private final UserInfoService userInfoService;

    private final ArticleInfoService articleInfoService;

    public UserPageController(UserInfoService userInfoService, ArticleInfoService articleInfoService) {
        this.userInfoService = userInfoService;
        this.articleInfoService = articleInfoService;
    }

    @GetMapping("/")
    public String userPage(@PathVariable String username, Model model) {
        model.addAttribute("userInfo", userInfoService.getUserInfoByUsername(username));
        // 如果找不到这个用户就返回404
        if (model.getAttribute("userInfo") == null) {
            return "error/404";
        }
        return "user-page";
    }

    @GetMapping("/get-user-open-articles")
    @ResponseBody
    public Map<String, Object> getUserOpenArticles(@PathVariable String username, Long page, Short limit,
                                                   @Nullable String field, @Nullable String order) {
        // 获取数据
        Page<ArticleInfo> articlesPage = new Page<>(page, limit);
        if (SecurityUtil.isUserSelf(username)) {
            // 如果是用户自己就转发给其他的接口去处理
            articleInfoService.getUserArticles(articlesPage, field, order);
        } else {
            // 否则就自己处理
            articleInfoService.getOpenArticlesByUsername(articlesPage, username, field, order);
        }
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的用户的博客");
        modelMap.put("count", articlesPage.getTotal());
        modelMap.put("data", articlesPage.getRecords());
        return modelMap;
    }

    // 编辑用户信息页面的获取
    @GetMapping("/edit-user")
    public String editUser(@PathVariable String username, Model model) {
        UserInfoVo userInfoVo = userInfoService.getUserInfoForEditByUsername(username);
        // 查不到或非法操作
        if (userInfoVo == null) {
            return "error/404";
        }
        model.addAttribute("userInfo", userInfoVo);
        return "edit-user";
    }

    // 编辑用户信息页面的提交
    @PostMapping("/edit-user")
    public String editUser(String newUsername,
                           String newPassword,
                           UserInfoVo userInfoVo) {
        Boolean res;
        if (SecurityUtil.isAdmin()) {
            // 如果是管理员就交给另外的接口来处理
            res = userInfoService.adminUpdateUserInfo(newUsername, newPassword, userInfoVo);
        } else {
            res = userInfoService.updateUserInfo(newUsername, newPassword, userInfoVo);
        }
        // 更新失败就跳到400
        return res ? "redirect:/user-page/" + newUsername + "/" : "error/400";
    }

}
