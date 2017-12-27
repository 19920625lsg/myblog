package com.huawei.l00379880.myblogbackend.service;

import com.huawei.l00379880.myblogbackend.entity.Comment;

import java.util.List;

/***********************************************************
 * @Description : 评论服务
 * @author      : 梁山广
 * @date        : 2017/12/27 18:59
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface CommentService {
    /**
     * 根据博客id获取其评论列表
     *
     * @param blogId 博客主键
     * @return 当前博客下的评论列表
     */
    List<Comment> getCommentListByBlogId(Long blogId);

    /**
     * 保存评论
     *
     * @param comment 评论对象
     * @return 保存成功返回原对象, 否则返回Null
     */
    Comment saveComment(Comment comment);
}
