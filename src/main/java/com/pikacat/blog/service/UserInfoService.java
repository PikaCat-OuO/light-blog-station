package com.pikacat.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.UserInfo;
import com.pikacat.blog.vo.UserInfoVo;

public interface UserInfoService {

    // 管理员获得所有用户
    void adminGetAllUserInfo(Page<UserInfo> userInfoPage, String field, String order);

    // 根据username查询用户
    UserInfo getUserInfoByUsername(String username);

    // 用户编辑自己的信息，因为管理员也有权限编辑，所以就将用户名传进来
    UserInfoVo getUserInfoForEditByUsername(String username);

    // 用户注册
    Boolean registerUser(UserInfoVo userInfoVo);

    // 用户信息修改
    Boolean updateUserInfo(String newUsername, String newPassword, UserInfoVo userInfoVo);

    // 管理员更新用户信息
    Boolean adminUpdateUserInfo(String newUsername, String newPassword, UserInfoVo userInfoVo);

    // 管理员删除用户信息
    void adminDeleteUserByUsername(String username);

    // 用户名重名检测
    Boolean isUsernameDuplicate(String username);

}
