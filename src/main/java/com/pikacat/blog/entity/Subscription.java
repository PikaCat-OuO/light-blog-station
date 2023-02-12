package com.pikacat.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Subscription {
    @TableId(type = IdType.AUTO)
    private Long subscriptionUid;
    private String username;
    private Long articleUid;

    public Subscription() {
    }

    public Subscription(Long subscriptionUid, String username, Long articleUid) {
        this.subscriptionUid = subscriptionUid;
        this.username = username;
        this.articleUid = articleUid;
    }

    public Long getSubscriptionUid() {
        return subscriptionUid;
    }

    public void setSubscriptionUid(Long subscriptionUid) {
        this.subscriptionUid = subscriptionUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getArticleUid() {
        return articleUid;
    }

    public void setArticleUid(Long articleUid) {
        this.articleUid = articleUid;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionUid=" + subscriptionUid +
                ", username='" + username + '\'' +
                ", articleUid=" + articleUid +
                '}';
    }

}