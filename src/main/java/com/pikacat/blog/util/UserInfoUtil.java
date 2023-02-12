package com.pikacat.blog.util;

import com.pikacat.blog.entity.UserInfo;
import com.pikacat.blog.vo.UserInfoVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserInfoUtil {

    // 检查用户的输入是否正确
    public static UserInfo userInfoCheckAndMap(UserInfoVo userInfoVo) {
        if ("".equals(userInfoVo.getUsername()) || userInfoVo.getUsername().length() < 1 || userInfoVo.getUsername().length() > 20 ||
                "".equals(userInfoVo.getPassword()) || userInfoVo.getPassword().length() < 8 || userInfoVo.getPassword().length() > 20 ||
                !"".equals(userInfoVo.getRealname()) && userInfoVo.getRealname().length() > 20 ||
                "".equals(userInfoVo.getGender()) || userInfoVo.getGender().length() > 1 ||
                userInfoVo.getAge() != null && userInfoVo.getAge() < 0 ||
                userInfoVo.getAge() != null && userInfoVo.getAge() > 150 ||
                !"".equals(userInfoVo.getTelephone()) && userInfoVo.getTelephone().length() < 7 ||
                !"".equals(userInfoVo.getTelephone()) && userInfoVo.getTelephone().length() > 11 ||
                !"".equals(userInfoVo.getAddress()) && userInfoVo.getAddress().length() > 40 ||
                !"".equals(userInfoVo.getDescription()) && userInfoVo.getDescription().length() > 200) {
            return null;
        }
        // 数据预处理
        UserInfo userInfo;
        try {
            userInfo = new UserInfo(
                    userInfoVo.getUsername(),
                    userInfoVo.getPassword(),
                    userInfoVo.getRealname(),
                    userInfoVo.getGender(),
                    userInfoVo.getAge(),
                    // 修正出生日期
                    new SimpleDateFormat("yyyy-MM-dd").parse(userInfoVo.getBirthday()),
                    userInfoVo.getTelephone(),
                    userInfoVo.getAddress(),
                    userInfoVo.getDescription()
            );
        } catch (ParseException e) {
            // 格式不对就返回null
            return null;
        }
        return userInfo;
    }
}
