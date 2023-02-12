package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.UserInfo;
import com.pikacat.blog.mapper.UserInfoMapper;
import com.pikacat.blog.util.NameUtil;
import com.pikacat.blog.util.SecurityUtil;
import com.pikacat.blog.util.UserInfoUtil;
import com.pikacat.blog.vo.UserInfoVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserInfoServiceImpl(UserInfoMapper userInfoMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userInfoMapper = userInfoMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void adminGetAllUserInfo(Page<UserInfo> userInfosPage, String field, String order) {
        // 管理员才能调用此接口，不用检测
        if (field == null) {
            // 按照默认的排序来排
            userInfoMapper.selectPage(userInfosPage, new QueryWrapper<UserInfo>()
                    .orderByAsc("birthday")
            );
        } else if (order.equals("")) {
            // 无序排列
            userInfoMapper.selectPage(userInfosPage, new QueryWrapper<>());
        } else {
            // 按照给定的字段来排序
            userInfoMapper.selectPage(userInfosPage, new QueryWrapper<UserInfo>()
                    .orderBy(true,
                            order.equals("asc"),
                            NameUtil.HumpToUnderline(field)
                    )
            );
        }
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("username", username)
        );
    }

    @Override
    public UserInfoVo getUserInfoForEditByUsername(String username) {
        if (SecurityUtil.isUserSelf(username)) {
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                    .eq("username", username)
            );
            return new UserInfoVo(
                    userInfo.getUsername(),
                    // 密码不回传
                    "",
                    userInfo.getRealname(),
                    userInfo.getGender(),
                    userInfo.getAge(),
                    // 修正出生日期
                    new SimpleDateFormat("yyyy-MM-dd").format(userInfo.getBirthday()),
                    userInfo.getTelephone(),
                    userInfo.getAddress(),
                    userInfo.getDescription()
            );
        }
        // 来捣乱的
        return null;
    }

    @Override
    public Boolean registerUser(UserInfoVo userInfoVo) {
        UserInfo userInfo = UserInfoUtil.userInfoCheckAndMap(userInfoVo);
        // 检查参数
        if (userInfo == null) {
            // 参数校验不通过
            return false;
        }
        // 密码用BCrypt加密
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userInfoMapper.insert(userInfo);
        return true;
    }

    @Override
    public Boolean updateUserInfo(String newUsername, String newPassword, UserInfoVo userInfoVo) {
        // 检查是不是用户自己在改，并且密码是否正确
        if (SecurityUtil.isUserSelf(userInfoVo.getUsername()) &&
                bCryptPasswordEncoder.matches(userInfoVo.getPassword(),
                        userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                                .eq("username", userInfoVo.getUsername())
                        ).getPassword()
                )
        ) {
            // 数据预处理，如果设置了新的密码
            if (!"".equals(newPassword)) {
                userInfoVo.setPassword(newPassword);
            }
            UserInfo userInfo = UserInfoUtil.userInfoCheckAndMap(userInfoVo);
            if (userInfo == null) {
                // 数据不正确
                return false;
            }
            // 密码用BCrypt加密
            userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
            userInfoMapper.updateById(userInfo);
            // 更新主键，不能相信，还要再检查一次看是否真的没有重名的
            if (!newUsername.equals(userInfo.getUsername()) &&
                    !isUsernameDuplicate(newUsername)
            ) {
                userInfoMapper.updateUsername(newUsername, userInfo.getUsername());
            }
            return true;
        }
        // 来捣乱的
        return false;
    }

    @Override
    public Boolean adminUpdateUserInfo(String newUsername, String newPassword, UserInfoVo userInfoVo) {
        // 数据预处理，如果设置了新的密码
        if (!"".equals(newPassword)) {
            userInfoVo.setPassword(newPassword);
        }
        UserInfo userInfo = UserInfoUtil.userInfoCheckAndMap(userInfoVo);
        if (userInfo == null) {
            // 数据不正确
            return false;
        }
        // 查询密码，还原密码
        UserInfo originUserInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("username", userInfo.getUsername())
        );
        userInfo.setPassword(bCryptPasswordEncoder.encode(originUserInfo.getPassword()));
        userInfoMapper.updateById(userInfo);
        // 更新主键，不能相信，还要再检查一次看是否真的没有重名的
        if (!newUsername.equals(userInfo.getUsername()) &&
                !isUsernameDuplicate(newUsername)
        ) {
            userInfoMapper.updateUsername(newUsername, userInfo.getUsername());
        }
        return true;
    }

    @Override
    public void adminDeleteUserByUsername(String username) {
        // 管理员直接过，不用检测
        userInfoMapper.delete(new QueryWrapper<UserInfo>()
                .eq("username", username)
        );
    }

    @Override
    public Boolean isUsernameDuplicate(String username) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("username", username)
        ) != null;
    }

}
