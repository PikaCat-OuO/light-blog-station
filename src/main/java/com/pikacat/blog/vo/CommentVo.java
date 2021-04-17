package com.pikacat.blog.vo;

import java.util.Date;

public class CommentVo{
    private Long commentUid;
    private Long articleUid;
    private String comment;
    private String username;
    private Date time;
    private Boolean removable;

    public CommentVo() {
    }

    public CommentVo(Long commentUid, Long articleUid, String comment, String username, Date time, Boolean removable) {
        this.commentUid = commentUid;
        this.articleUid = articleUid;
        this.comment = comment;
        this.username = username;
        this.time = time;
        this.removable = removable;
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

    public Boolean getRemovable() {
        return removable;
    }

    public void setRemovable(Boolean removable) {
        this.removable = removable;
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "commentUid=" + commentUid +
                ", articleUid=" + articleUid +
                ", comment='" + comment + '\'' +
                ", username='" + username + '\'' +
                ", time=" + time +
                ", removable=" + removable +
                '}';
    }

}
