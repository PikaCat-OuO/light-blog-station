package com.pikacat.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class Comment {
    @TableId(type = IdType.AUTO)
    private Long commentUid;
    private Long articleUid;
    private String comment;
    private String username;
    private Date time;

    public Comment() {
    }

    public Comment(Long commentUid, Long articleUid, String comment, String username, Date time) {
        this.commentUid = commentUid;
        this.articleUid = articleUid;
        this.comment = comment;
        this.username = username;
        this.time = time;
    }

    public Long getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(Long commentUid) {
        this.commentUid = commentUid;
    }

    public Long getArticleUid() {
        return articleUid;
    }

    public void setArticleUid(Long articleUid) {
        this.articleUid = articleUid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentUid=" + commentUid +
                ", articleUid=" + articleUid +
                ", comment='" + comment + '\'' +
                ", username='" + username + '\'' +
                ", time=" + time +
                '}';
    }

}
