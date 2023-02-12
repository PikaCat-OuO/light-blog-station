package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.entity.Subscription;
import com.pikacat.blog.mapper.ArticleInfoMapper;
import com.pikacat.blog.mapper.SubscriptionMapper;
import com.pikacat.blog.util.NameUtil;
import com.pikacat.blog.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    private final ArticleInfoMapper articleInfoMapper;

    private final SubscriptionMapper subscriptionMapper;

    public ArticleInfoServiceImpl(ArticleInfoMapper articleInfoMapper, SubscriptionMapper subscriptionMapper) {
        this.articleInfoMapper = articleInfoMapper;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public void adminGetAllArticles(Page<ArticleInfo> articlesPage, String field, String order) {
        if (field == null) {
            // 按照默认的排序来排
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .orderByDesc("modify_time", "create_time")
            );
        } else if (order.equals("")) {
            // 无序排列
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<>());
        } else {
            // 按照给定的字段来排序
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .orderBy(true,
                            order.equals("asc"),
                            NameUtil.HumpToUnderline(field)
                    )
            );
        }
    }

    @Override
    public void getUserArticles(Page<ArticleInfo> articlesPage, String field, String order) {
        if (field == null) {
            // 按照默认的排序来排
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("username", SecurityUtil.getCurrentUserName())
                    .orderByDesc("modify_time", "create_time")
            );
        } else if (order.equals("")) {
            // 无序排列
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("username", SecurityUtil.getCurrentUserName())
            );
        } else {
            // 按照给定的字段来排序
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("username", SecurityUtil.getCurrentUserName())
                    .orderBy(true,
                            order.equals("asc"),
                            NameUtil.HumpToUnderline(field)
                    )
            );
        }
    }

    @Override
    public void getOpenArticlesByUsername(Page<ArticleInfo> articlesPage, String username, String field, String order) {
        if (field == null) {
            // 按照默认的排序来排
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("username", username)
                    .eq("open", true)
                    .orderByDesc("modify_time", "create_time")
            );
        } else if (order.equals("")) {
            // 无序排列
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("username", username)
                    .eq("open", true)
            );
        } else {
            // 按照给定的字段来排序
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("username", username)
                    .eq("open", true)
                    .orderBy(true,
                            order.equals("asc"),
                            NameUtil.HumpToUnderline(field)
                    )
            );
        }
    }

    @Override
    public ArticleInfo getArticleByArticleUid(Long articleUid) {
        ArticleInfo articleInfo = articleInfoMapper.selectOne(new QueryWrapper<ArticleInfo>()
                .eq("article_uid", articleUid)
        );
        if (articleInfo == null) {
            // 查不到说明有人在乱调用接口，直接返回即可
            return null;
        }
        // 如果文章是公开的就可以直接返回
        if (articleInfo.getOpen()) {
            return articleInfo;
        }
        // 如果文章不是公开的就要校验用户身份
        if (SecurityUtil.isUserSelf(articleInfo.getUsername())) {
            // 校验通过，可以返回
            return articleInfo;
        }
        // 校验失败，来捣乱的
        return null;
    }

    @Override
    public ArticleInfo getArticleForEditByArticleUid(Long articleUid) {
        ArticleInfo articleInfo = articleInfoMapper.selectOne(new QueryWrapper<ArticleInfo>()
                .eq("article_uid", articleUid)
        );
        if (articleInfo == null) {
            // 查不到说明有人在乱调用接口，直接返回即可
            return null;
        }
        // 校验用户身份
        if (SecurityUtil.isUserSelf(articleInfo.getUsername())) {
            // 校验通过，可以返回
            return articleInfo;
        }
        // 校验失败，来捣乱的
        return null;
    }

    @Override
    public void getHotOpenArticles(Page<ArticleInfo> articlesPage, String field, String order) {
        if (field == null) {
            // 按照默认的排序来排
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("open", true)
                    .orderByDesc("modify_time", "create_time")
            );
        } else if (order.equals("")) {
            // 无序排列
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("open", true)
            );
        } else {
            // 按照给定的字段来排序
            articleInfoMapper.selectPage(articlesPage, new QueryWrapper<ArticleInfo>()
                    .eq("open", true)
                    .orderBy(true,
                            order.equals("asc"),
                            NameUtil.HumpToUnderline(field)
                    )
            );
        }
    }

    @Override
    public void adminDeleteArticleByArticleUid(Long articleUid) {
        // 管理员才能调用这个接口，不用检测
        articleInfoMapper.delete(new QueryWrapper<ArticleInfo>()
                .eq("article_uid", articleUid)
        );
    }

    @Override
    public void deleteArticleByArticleUid(Long articleUid) {
        // 删除文章
        articleInfoMapper.delete(new QueryWrapper<ArticleInfo>()
                .eq("username", SecurityUtil.getCurrentUserName())
                .eq("article_uid", articleUid)
        );
    }

    @Override
    public void editArticle(ArticleInfo articleInfo) {
        // 校验数据合法性
        if (articleInfo.getTitle().length() > 30 ||
                articleInfo.getContent().length() > 20000 ||
                articleInfo.getCategory().length() > 10 ||
                articleInfo.getKeywords().length() > 20) {
            // 长度不满足要求
            return;
        }
        // 如果用户设置文章不公开
        if (articleInfo.getOpen() == null) {
            articleInfo.setOpen(false);
        }
        // 如果用户设置文章不可评论
        if (articleInfo.getCommentable() == null) {
            articleInfo.setCommentable(false);
        }
        // 根据所给的articleInfo获得articleUid，并检查是否是当前用户的文章
        ArticleInfo originArticleInfo = articleInfoMapper.selectOne(
                new QueryWrapper<ArticleInfo>()
                        .eq("article_uid", articleInfo.getArticleUid())
        );
        // 检查文章是否存在
        if (originArticleInfo == null) {
            // 文章不存在，有人在捣乱
            return;
        }
        // 文章存在，确保是当前用户自己的文章
        // 检验是否是用户本人
        if (SecurityUtil.isUserSelf(originArticleInfo.getUsername())) {
            // 是用户本人，修正文章的username
            articleInfo.setUsername(SecurityUtil.getCurrentUserName());
            // 更新修改时间
            articleInfo.setModifyTime(new Date());
            // 查询所有关注，删除对应的关注
            subscriptionMapper.delete(new QueryWrapper<Subscription>()
                    .eq("article_uid", articleInfo.getArticleUid())
            );
            // 做修改
            articleInfoMapper.update(articleInfo, new UpdateWrapper<ArticleInfo>()
                    .eq("article_uid", articleInfo.getArticleUid())
            );
        }
        // 不是用户本人，是来捣乱的
    }

    @Override
    public void adminEditArticle(ArticleInfo articleInfo) {
        // 校验数据合法性
        if (articleInfo.getTitle().length() > 30 ||
                articleInfo.getContent().length() > 20000 ||
                articleInfo.getCategory().length() > 10 ||
                articleInfo.getKeywords().length() > 20) {
            // 长度不满足要求
            return;
        }
        // 如果用户设置文章不公开
        if (articleInfo.getOpen() == null) {
            articleInfo.setOpen(false);
        }
        // 如果用户设置文章不可评论
        if (articleInfo.getCommentable() == null) {
            articleInfo.setCommentable(false);
        }
        // 根据所给的articleInfo获得articleUid
        ArticleInfo originArticleInfo = articleInfoMapper.selectOne(
                new QueryWrapper<ArticleInfo>()
                        .eq("article_uid", articleInfo.getArticleUid())
        );
        // 检查文章是否存在
        if (originArticleInfo == null) {
            // 文章不存在，有人在捣乱
            return;
        }
        // 文章存在，管理员无需验证
        // 修正文章的username
        articleInfo.setUsername(originArticleInfo.getUsername());
        // 查询所有关注，删除对应的关注
        subscriptionMapper.delete(new QueryWrapper<Subscription>()
                .eq("article_uid", articleInfo.getArticleUid())
        );
        // 更新修改时间
        articleInfo.setModifyTime(new Date());
        // 做修改
        articleInfoMapper.update(articleInfo, new UpdateWrapper<ArticleInfo>()
                .eq("article_uid", articleInfo.getArticleUid())
        );
    }

    @Override
    public void addArticle(ArticleInfo articleInfo) {
        // 校验数据合法性
        if (articleInfo.getTitle().length() > 30 ||
                articleInfo.getContent().length() > 20000 ||
                articleInfo.getCategory().length() > 10 ||
                articleInfo.getKeywords().length() > 20) {
            // 长度不满足要求
            return;
        }
        // 如果用户设置文章不公开
        if (articleInfo.getOpen() == null) {
            articleInfo.setOpen(false);
        }
        // 如果用户设置文章不可评论
        if (articleInfo.getCommentable() == null) {
            articleInfo.setCommentable(false);
        }
        // 修正文章的userUid
        articleInfo.setUsername(SecurityUtil.getCurrentUserName());
        // 修正文章的创建时间
        articleInfo.setCreateTime(new Date());
        // 更新修改时间
        articleInfo.setModifyTime(new Date());
        // 插入数据库
        articleInfoMapper.insert(articleInfo);
    }

}
