package com.pikacat.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.service.ArticleInfoService;
import com.pikacat.blog.service.CategoryService;
import com.pikacat.blog.service.UserInfoService;
import com.pikacat.blog.util.FileUtil;
import com.pikacat.blog.util.SecurityUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

// 我的文章控制器
@Controller
@RequestMapping("/my-articles")
public class MyArticlesController {

    private final ArticleInfoService articleInfoService;

    private final UserInfoService userInfoService;

    private final CategoryService categoryService;

    public MyArticlesController(ArticleInfoService articleInfoService,
                                UserInfoService userInfoService,
                                CategoryService categoryService) {
        this.articleInfoService = articleInfoService;
        this.userInfoService = userInfoService;
        this.categoryService = categoryService;
    }

    // 我的博客页面
    @GetMapping("")
    public String myArticles() {
        return "my-articles";
    }

    // 获得用户的全部博客
    @GetMapping("/get-user-articles")
    @ResponseBody
    public Map<String, Object> getUserArticles(Long page, Short limit, @Nullable String field, @Nullable String order) {
        // 获取数据
        Page<ArticleInfo> articlesPage = new Page<>(page, limit);
        articleInfoService.getUserArticles(articlesPage, field, order);
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的用户博客");
        modelMap.put("count", articlesPage.getTotal());
        modelMap.put("data", articlesPage.getRecords());
        return modelMap;
    }

    // 新发表博客
    @GetMapping("/new-article")
    public String newArticle(Model model) {
        // 查询对应的分类信息
        model.addAttribute("category", categoryService.getAllCategory());
        return "new-article";
    }

    // 博客提交
    @PostMapping("/new-article")
    public String newArticle(ArticleInfo articleInfo) {
        articleInfoService.addArticle(articleInfo);
        return "redirect:/article/" + articleInfo.getArticleUid() + "/";
    }

    // 文章编辑页面
    @GetMapping("/edit-article/{articleUid}")
    public String editArticle(@PathVariable Long articleUid, Model model) {
        ArticleInfo articleInfo = articleInfoService.getArticleForEditByArticleUid(articleUid);
        // 如果查不到或非法操作就返回404页面
        if (articleInfo == null) {
            return "error/404";
        }
        model.addAttribute("article", articleInfo);
        model.addAttribute("author",
                userInfoService.getUserInfoByUsername(articleInfo.getUsername()));
        // 查询对应的分类信息
        model.addAttribute("category", categoryService.getAllCategory());
        return "edit-article";
    }

    // 编辑后的提交页面
    @PostMapping("/edit-article/{articleUid}")
    public String editArticle(ArticleInfo articleInfo) {
        if (SecurityUtil.isAdmin()) {
            // 管理员另外调用接口处理
            articleInfoService.adminEditArticle(articleInfo);
        } else {
            articleInfoService.editArticle(articleInfo);
        }
        return "redirect:/article/" + articleInfo.getArticleUid() + "/";
    }

    // 删除文章
    @GetMapping("/delete-article/{articleUid}")
    @ResponseBody
    public void deleteArticle(@PathVariable Long articleUid) {
        articleInfoService.deleteArticleByArticleUid(articleUid);
    }

    // 文件上传接口
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam("edit") MultipartFile file) {
        Map<String, Object> modelMap = new HashMap<>();
        String location = FileUtil.saveImage(file);
        if (location.startsWith("/")) {
            modelMap.put("code", 0);
            modelMap.put("msg", "上传成功");
            modelMap.put("data", location);
        } else {
            modelMap.put("code", 1);
            modelMap.put("msg", location);
        }
        return modelMap;
    }

}
