package com.huawei.l00379880.myblogbackend.controller;

import com.huawei.l00379880.myblogbackend.entity.Comment;
import com.huawei.l00379880.myblogbackend.entity.User;
import com.huawei.l00379880.myblogbackend.service.BlogService;
import com.huawei.l00379880.myblogbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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
    public String post(Comment comment, HttpSession session) {
        // 前端传过来的只有
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        // 看看管理员是否登录
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdmin(true);
        } else {
            // 管理员没登录
            // 获取该昵称的头像,如果之前存在的话就用之前的
            comment.setAvatar(commentService.getAvatar(comment.getNickname(), comment.getEmail()));
            // 不是管理员
            comment.setAdmin(false);
        }

        commentService.saveComment(comment);
        // 传入"blog.id"
        return "redirect:/comments/" + comment.getBlog().getId();
    }
}
