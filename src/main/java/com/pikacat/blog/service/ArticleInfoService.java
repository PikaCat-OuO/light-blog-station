package com.pikacat.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;

public interface ArticleInfoService {

    // 管理员获得所有的博客
    void adminGetAllArticles(Page<ArticleInfo> articlesPage, String field, String order);

    // 获得某个用户对应的所有博客
    void getUserArticles(Page<ArticleInfo> articlesPage, String field, String order);

    // 获得某个用户对应的公开博客
    void getOpenArticlesByUsername(Page<ArticleInfo> articlesPage, String username, String field, String order);

    // 获得某个具体的博客
    ArticleInfo getArticleByArticleUid(Long articleUid);

    // 获得某个具体的博客用于编辑
    ArticleInfo getArticleForEditByArticleUid(Long articleUid);

    // 获得热点博客的简略信息
    void getHotOpenArticles(Page<ArticleInfo> articlesPage, String field, String order);

    // 管理员删除博客
    void adminDeleteArticleByArticleUid(Long articleUid);

    // 根据articleUid删除articles
    void deleteArticleByArticleUid(Long articleUid);

    // 修改文章
    void editArticle(ArticleInfo articleInfo);

    // 管理员修改文章
    void adminEditArticle(ArticleInfo articleInfo);

    // 新增文章
    void addArticle(ArticleInfo articleInfo);
}
