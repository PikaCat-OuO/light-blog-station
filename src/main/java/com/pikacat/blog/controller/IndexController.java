package com.pikacat.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.service.ArticleInfoService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//首页控制器
@Controller
@RequestMapping("/")
public class IndexController {

    private final ArticleInfoService articleInfoService;

    public IndexController(ArticleInfoService articleInfoService) {
        this.articleInfoService = articleInfoService;
    }

    // 首页
    @GetMapping("")
    public String index() {
        return "index";
    }

    // 获取热点信息
    @GetMapping("get-hot-open-articles")
    @ResponseBody
    public Map<String, Object> getHotOpenArticles(Long page, Short limit,
                                                  @Nullable String field,
                                                  @Nullable String order) {
        // 获取数据
        Page<ArticleInfo> articlesPage = new Page<>(page, limit);
        articleInfoService.getHotOpenArticles(articlesPage, field, order);
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的热点博客");
        modelMap.put("count", articlesPage.getTotal());
        modelMap.put("data", articlesPage.getRecords());
        return modelMap;
    }

}
