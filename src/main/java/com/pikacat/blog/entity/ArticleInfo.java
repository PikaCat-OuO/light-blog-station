package com.pikacat.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class ArticleInfo {
    @TableId(type = IdType.AUTO)
    private Long articleUid;
    private String title;
    private String content;
    private String category;
    private String keywords;
    private Boolean open;
    private Boolean commentable;
    private Date createTime;
    private Date modifyTime;
    private String username;

    public ArticleInfo() {
    }

    public ArticleInfo(Long articleUid, String title, String content, String category, String keywords, Boolean open, Boolean commentable, Date createTime, Date modifyTime, String username) {
        this.articleUid = articleUid;
        this.title = title;
        this.content = content;
        this.category = category;
        this.keywords = keywords;
        this.open = open;
        this.commentable = commentable;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.username = username;
    }

    public Long getArticleUid() {
        return articleUid;
    }

    public void setArticleUid(Long articleUid) {
        this.articleUid = articleUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "articleUid=" + articleUid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", keywords='" + keywords + '\'' +
                ", open=" + open +
                ", commentable=" + commentable +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", username='" + username + '\'' +
                '}';
    }

}
