package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.entity.Subscription;
import com.pikacat.blog.mapper.ArticleInfoMapper;
import com.pikacat.blog.mapper.SubscriptionMapper;
import com.pikacat.blog.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionMapper subscriptionMapper;

    private final ArticleInfoMapper articleInfoMapper;

    public SubscriptionServiceImpl(SubscriptionMapper subscriptionMapper,
                                   ArticleInfoMapper articleInfoMapper) {
        this.subscriptionMapper = subscriptionMapper;
        this.articleInfoMapper = articleInfoMapper;
    }

    @Override
    public Boolean isSubscribed(Long articleUid) {
        return subscriptionMapper.selectOne(new QueryWrapper<Subscription>()
                .eq("username", SecurityUtil.getCurrentUserName())
                .eq("article_uid", articleUid)
        ) != null;
    }

    @Override
    public void subscribeArticle(Long articleUid) {
        // 先看一下该用户是否已经订阅了这个文章
        if (isSubscribed(articleUid)) {
            return;
        }
        Subscription subscription = new Subscription();
        subscription.setUsername(SecurityUtil.getCurrentUserName());
        subscription.setArticleUid(articleUid);
        subscriptionMapper.insert(subscription);
    }

    @Override
    public void unsubscribeArticle(Long articleUid) {
        // 先看一下该用户是否已经订阅了这个文章
        if (isSubscribed(articleUid)) {
            subscriptionMapper.delete(new QueryWrapper<Subscription>()
                    .eq("username", SecurityUtil.getCurrentUserName())
                    .eq("article_uid", articleUid)
            );
        }
    }

    @Override
    public Page<ArticleInfo> searchSubscription(Page<Subscription> subscriptionPage) {
        // 根据当前用户查出他订阅的文章
        subscriptionMapper.selectPage(subscriptionPage, new QueryWrapper<Subscription>()
                .eq("username", SecurityUtil.getCurrentUserName())
        );

        // 根据查出来的数据查询订阅的文章
        Page<ArticleInfo> articleInfoPage = new Page<>(subscriptionPage.getCurrent(), subscriptionPage.getSize());

        if (subscriptionPage.getRecords().size() > 0) {
            articleInfoMapper.selectPage(articleInfoPage, new QueryWrapper<ArticleInfo>()
                    .in("article_uid", subscriptionPage.getRecords().stream().map(Subscription::getArticleUid).collect(Collectors.toList()))
            );
        }
        // 修正页面信息
        articleInfoPage.setPages(subscriptionPage.getPages());
        articleInfoPage.setTotal(subscriptionPage.getTotal());
        return articleInfoPage;

    }

}
