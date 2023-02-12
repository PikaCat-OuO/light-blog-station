package com.pikacat.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.Category;

import java.util.List;

public interface CategoryService {

    // 获得当前所有分类
    List<Category> getAllCategory();

    // 管理员获得当前的所有分类
    void adminGetAllCategory(Page<Category> categoryPage);

    // 检查是否有当前分类
    Boolean isCategoryDuplicate(String category);

    // 添加分类
    void addCategory(String category);

    // 管理员删除分类
    void adminDeleteCategory(String category);

    // 管理员修改分类
    void adminEditCategory(String newCategory, String oldCategory);

}
