package com.pikacat.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.entity.Comment;
import com.pikacat.blog.mapper.ArticleInfoMapper;
import com.pikacat.blog.mapper.CommentMapper;
import com.pikacat.blog.util.SecurityUtil;
import com.pikacat.blog.vo.CommentVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final ArticleInfoMapper articleInfoMapper;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper,
                              ArticleInfoMapper articleInfoMapper) {
        this.commentMapper = commentMapper;
        this.articleInfoMapper = articleInfoMapper;
    }

    @Override
    public List<CommentVo> getCommentsByArticleUid(Long articleUid, Page<Comment> commentPage) {
        // 根据articleUid查询对应的文章
        ArticleInfo articleInfo = articleInfoMapper.selectOne(new QueryWrapper<ArticleInfo>()
                .eq("article_uid", articleUid)
        );

        // 查看文章是否是公开的，如果不是公开的就要检查是否是用户自己
        if (articleInfo.getOpen() || SecurityUtil.isUserSelf(articleInfo.getUsername())) {
            commentMapper.selectPage(commentPage, new QueryWrapper<Comment>()
                    .eq("article_uid", articleUid)
                    .orderByDesc("time")
            );
        }

        // 封装数据
        List<CommentVo> comments = new ArrayList<>();
        if (commentPage.getRecords().size() != 0) {
            for (Comment comment : commentPage.getRecords()) {
                // 检查这个评论能否被当前用户删除，即评论是不是自己发的或者是自己的文章
                comments.add(new CommentVo(
                                comment.getCommentUid(),
                                comment.getArticleUid(),
                                comment.getComment(),
                                comment.getUsername(),
                                comment.getTime(),
                                SecurityUtil.isUserSelf(comment.getUsername()) ||
                                        SecurityUtil.isUserSelf(articleInfo.getUsername())
                        )
                );
            }
        }
        return comments;
    }

    @Override
    public void addCommentByArticleUid(Comment comment) {
        // 根据articleUid查询文章的信息
        ArticleInfo articleInfo = articleInfoMapper.selectOne(new QueryWrapper<ArticleInfo>()
                .eq("article_uid", comment.getArticleUid())
        );
        // 查看该文章是否允许评论
        if (articleInfo.getCommentable()) {
            // 允许评论，检查文章的公开状态，如果不是公开的，就要看是否是用户自己的文章
            if (articleInfo.getOpen() || SecurityUtil.isUserSelf(articleInfo.getUsername())) {
                // 开始修正username
                comment.setUsername(SecurityUtil.getCurrentUserName());
                // 修正评论日期
                comment.setTime(new Date());
                commentMapper.insert(comment);
            }
            // 非公开，允许评论，但不是用户自己的文章，来捣乱的
        }
        // 不允许评论，来捣乱的
    }

    @Override
    public void removeCommentByCommentUid(Long commentUid) {
        // 根据commentUid查询对应的comment
        Comment comment = commentMapper.selectOne(new QueryWrapper<Comment>()
                .eq("comment_uid", commentUid)
        );

        // 如果查不到就说明有人在捣乱
        if (comment == null) {
            return;
        }

        ArticleInfo articleInfo = articleInfoMapper.selectOne(new QueryWrapper<ArticleInfo>()
                .eq("article_uid", comment.getArticleUid())
        );

        // 检查是否有删除的权限，文章是否是自己的或评论是否是自己的
        if (SecurityUtil.isUserSelf(articleInfo.getUsername()) ||
                SecurityUtil.isUserSelf(comment.getUsername())) {
            commentMapper.delete(new QueryWrapper<Comment>()
                    .eq("comment_uid", commentUid)
            );
        }
    }

}
