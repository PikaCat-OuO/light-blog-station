package com.pikacat.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pikacat.blog.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    // 更改用户名
    @Update("UPDATE admin SET username = #{newUsername} WHERE username = #{oldUsername}")
    void updateUsername(@Param("newUsername") String newUsername, @Param("oldUsername") String oldUsername);

}
