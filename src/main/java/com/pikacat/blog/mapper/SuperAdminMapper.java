package com.pikacat.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pikacat.blog.entity.SuperAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SuperAdminMapper extends BaseMapper<SuperAdmin> {

    // 更改用户名
    @Update("UPDATE super_admin SET username = #{newUsername} WHERE username = #{oldUsername}")
    void updateUsername(@Param("newUsername") String newUsername, @Param("oldUsername") String oldUsername);

}
