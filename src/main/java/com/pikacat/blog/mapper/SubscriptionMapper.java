package com.pikacat.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pikacat.blog.entity.Subscription;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscriptionMapper extends BaseMapper<Subscription> {
}
