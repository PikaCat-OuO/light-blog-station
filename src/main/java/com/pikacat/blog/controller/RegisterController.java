package com.pikacat.blog.controller;

import com.pikacat.blog.service.UserInfoService;
import com.pikacat.blog.vo.UserInfoVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


// 注册控制器
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserInfoService userInfoService;

    public RegisterController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    // 注册页面的获取
    @GetMapping("")
    public String register() {
        return "register";
    }

    // 用户注册请求的提交
    @PostMapping("")
    public String register(UserInfoVo userInfoVo) {
        if (userInfoService.registerUser(userInfoVo)) {
            return "redirect:/login";
        }
        return "error/400";
    }

    @GetMapping("/{username}")
    @ResponseBody
    public Boolean isDuplicate(@PathVariable String username) {
        return userInfoService.isUsernameDuplicate(username);
    }

}
