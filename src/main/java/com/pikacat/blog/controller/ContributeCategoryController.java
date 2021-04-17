package com.pikacat.blog.controller;

import com.pikacat.blog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("contribute-category")
public class ContributeCategoryController {

    private final CategoryService categoryService;

    public ContributeCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String contributeCategory() {
        return "contribute-category";
    }

    @PostMapping("")
    public String contributeCategory(String category) {
        categoryService.addCategory(category);
        return "redirect:/";
    }

    @GetMapping("/{category}")
    @ResponseBody
    public Boolean isDuplicate(@PathVariable String category) {
        return categoryService.isCategoryDuplicate(category);
    }

}
