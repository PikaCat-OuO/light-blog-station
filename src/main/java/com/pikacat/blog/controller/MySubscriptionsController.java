package com.pikacat.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.entity.Subscription;
import com.pikacat.blog.service.SubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/my-subscriptions")
public class MySubscriptionsController {

    private final SubscriptionService subscriptionService;

    public MySubscriptionsController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("")
    public String mySubscriptions() {
        return "my-subscriptions";
    }

    // 获取用户的所有关注
    @GetMapping("/get-subscriptions")
    @ResponseBody
    public Map<String, Object> article(Long page, Short limit) {
        Page<ArticleInfo> articleInfoPage = subscriptionService.searchSubscription(new Page<Subscription>(page, limit));
        // 封装数据
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("code", 0);
        modelMap.put("msg", "获取到的关注");
        modelMap.put("count", articleInfoPage.getTotal());
        modelMap.put("data", articleInfoPage.getRecords());
        return modelMap;
    }

}
