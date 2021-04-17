package com.pikacat.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pikacat.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    @Update("UPDATE category SET name = #{newCategory} WHERE name = #{oldCategory}")
    void updateCategory(@Param("newCategory") String newCategory,
                        @Param("oldCategory") String oldCategory);

}
