package com.pikacat.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pikacat.blog.entity.ArticleInfo;
import com.pikacat.blog.entity.Comment;
import com.pikacat.blog.entity.UserInfo;
import com.pikacat.blog.service.ArticleInfoService;
import com.pikacat.blog.service.CommentService;
import com.pikacat.blog.service.SubscriptionService;
import com.pikacat.blog.service.UserInfoService;
import com.pikacat.blog.vo.CommentVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article/{articleUid}")
public class ArticleController {

    private final ArticleInfoService articleInfoService;

    private final UserInfoService userInfoService;

    private final CommentService commentService;

    private final SubscriptionService subscriptionService;

    public ArticleController(ArticleInfoService articleInfoService,
                             UserInfoService userInfoService,
                             CommentService commentService,
                             SubscriptionService subscriptionService) {
        this.articleInfoService = articleInfoService;
        this.userInfoService = userInfoService;
        this.commentService = commentService;
        this.subscriptionService = subscriptionService;
    }

    // 获取文章
    @GetMapping("/")
    public String article(@PathVariable Long articleUid, Model model) {
        ArticleInfo articleInfo = articleInfoService.getArticleByArticleUid(articleUid);
        UserInfo userInfo = userInfoService.getUserInfoByUsername(articleInfo.getUsername());
        // 返回是否已经订阅的信息
        model.addAttribute("subscribed", subscriptionService.isSubscribed(articleUid));
        model.addAttribute("article", articleInfo);
        model.addAttribute("author", userInfo);
        return "article";
    }

    // 往文章上加评论
    @PostMapping("/add-comment")
    public String addCommentsByArticleUid(Comment comment) {
        commentService.addCommentByArticleUid(comment);
        return "redirect:/article/" + comment.getArticleUid() + "/";
    }

    @GetMapping("/get-comments/{page}")
    @ResponseBody
    public Map<String, Object> getCommentsByArticleUid(@PathVariable Long articleUid,
                                                       @PathVariable Long page) {
        Page<Comment> commentPage = new Page<>(page, 20);
        // 获取数据，选择一次20条与B站保持一致
        List<CommentVo> comments = commentService.getCommentsByArticleUid(articleUid, commentPage);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("pages", commentPage.getPages());
        modelMap.put("comments", comments);
        return modelMap;
    }

    @GetMapping("/remove-comment/{commentUid}")
    public String removeCommentByCommentUid(@PathVariable Long articleUid,
                                            @PathVariable Long commentUid) {
        commentService.removeCommentByCommentUid(commentUid);
        return "redirect:/article/" + articleUid + "/";
    }

    @GetMapping("/subscribe")
    public String subscribe(@PathVariable Long articleUid) {
        subscriptionService.subscribeArticle(articleUid);
        return "redirect:/article/{articleUid}/";
    }

    @GetMapping("/unsubscribe")
    public String unsubscribe(@PathVariable Long articleUid) {
        subscriptionService.unsubscribeArticle(articleUid);
        return "redirect:/article/{articleUid}/";
    }

}
