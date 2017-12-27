package com.huawei.l00379880.myblogbackend.controller;

import com.huawei.l00379880.myblogbackend.entity.Comment;
import com.huawei.l00379880.myblogbackend.service.BlogService;
import com.huawei.l00379880.myblogbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

/***********************************************************
 * @Description : 评论的相关接口
 * @author      : 梁山广
 * @date        : 2017/12/27 18:43
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        // 获取指定博客下的评论列表
        model.addAttribute("comments", commentService.getCommentListByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment) {
        // 前端传过来的只有
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        commentService.saveComment(comment);
        // 生成[0,10]之间的整数,然后我们自己组成avatar的路径,随机选一个头像
        int avatarNum = new Random().nextInt(15);
        // 随机选一个static/images/avatar/路径下的一个图片,作为头像
        String avatar = "/images/avatar/avatar" + avatarNum + ".jpg";
        comment.setAvatar(avatar);
        // 传入"blog.id"
        return "redirect:/comments/" + comment.getBlog().getId();
    }
}
