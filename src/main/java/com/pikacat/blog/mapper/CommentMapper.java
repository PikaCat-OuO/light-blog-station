package com.pikacat.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pikacat.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
