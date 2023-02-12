package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.Category;
import com.pikacat.blog.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.selectList(null);
    }

    @Override
    public void adminGetAllCategory(Page<Category> categoryPage) {
        categoryMapper.selectPage(categoryPage, new QueryWrapper<>());
    }

    @Override
    public Boolean isCategoryDuplicate(String category) {
        return categoryMapper.selectOne(new QueryWrapper<Category>()
                .eq("name", category)
        ) != null;
    }

    @Override
    public void addCategory(String category) {
        // 信不过，再查一下
        if (!isCategoryDuplicate(category) && category.length() >= 1) {
            Category newCategory = new Category(category);
            categoryMapper.insert(newCategory);
        }
    }

    @Override
    public void adminDeleteCategory(String category) {
        categoryMapper.delete(new QueryWrapper<Category>()
                .eq("name", category)
        );
    }

    @Override
    public void adminEditCategory(String newCategory, String oldCategory) {
        if (newCategory.length() >= 1) {
            categoryMapper.updateCategory(newCategory, oldCategory);
        }
    }

}
