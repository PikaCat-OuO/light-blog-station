package com.pikacat.blog.controller;

import com.pikacat.blog.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 登录控制器
@Controller
@RequestMapping("/login")
public class LoginController {

    // 登录页
    @GetMapping("")
    public String login() {
        // 检查用户是否已经登录了，如果登录了直接跳转到主页
        if (SecurityUtil.isLogined()) {
            return "redirect:/";
        }
        return "login";
    }

}
