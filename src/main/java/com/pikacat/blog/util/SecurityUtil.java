package com.pikacat.blog.util;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityUtil {

    // 获得用户的权限，没有登录返回ROLE_ANONYMOUS
    public static String getCurrentUserAuthority() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().toList().get(0).getAuthority();
    }

    // 获取用户名，没有登录返回anonymousUser
    public static String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // 检查用户是否登录
    public static Boolean isLogined() {
        return List.of("USER", "ADMIN", "SUPER_ADMIN").contains(getCurrentUserAuthority());
    }

    // 检查用户是否管理员或本人
    public static Boolean isUserSelf(String userName) {
        if (isLogined()) {
            // 登录了就验证是否是管理员或本人
            return isAdmin() || userName.equals(getCurrentUserName());
        } else {
            // 没登录就返回假
            return false;
        }
    }

    // 检查是否是管理员
    public static Boolean isAdmin() {
        return List.of("ADMIN", "SUPER_ADMIN").contains(getCurrentUserAuthority());
    }

}
