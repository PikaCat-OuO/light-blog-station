package com.pikacat.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pikacat.blog.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    // 将旧用户名改为新的用户名
    @Update("UPDATE user_info SET username = #{newUsername} WHERE username = #{oldUsername}")
    void updateUsername(@Param("newUsername") String newUsername,
                       @Param("oldUsername") String oldUsername);

}
