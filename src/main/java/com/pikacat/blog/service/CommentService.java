package com.pikacat.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.Comment;
import com.pikacat.blog.vo.CommentVo;

import java.util.List;

public interface CommentService {

    // 根据ArticleUid获取对应的文章的评论
    List<CommentVo> getCommentsByArticleUid(Long articleUid, Page<Comment> commentPage);

    // 添加评论
    void addCommentByArticleUid(Comment comment);

    // 删除评论，返回一个articleUid用于前端跳转
    void removeCommentByCommentUid(Long commentUid);
}
