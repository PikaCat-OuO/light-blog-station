package com.pikacat.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.entity.Subscription;

public interface SubscriptionService {

    // 是否已经订阅
    Boolean isSubscribed(Long articleUid);

    // 新增关注
    void subscribeArticle(Long articleUid);

    // 取消关注
    void unsubscribeArticle(Long articleUid);

    // 查询关注
    Page<ArticleInfo> searchSubscription(Page<Subscription> subscriptionPage);

}
