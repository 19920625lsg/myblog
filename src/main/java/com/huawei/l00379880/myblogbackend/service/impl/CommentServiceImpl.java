package com.huawei.l00379880.myblogbackend.service.impl;

import com.huawei.l00379880.myblogbackend.entity.Comment;
import com.huawei.l00379880.myblogbackend.repository.CommentRepository;
import com.huawei.l00379880.myblogbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/***********************************************************
 * @Description : 评论服务的设置类
 *                Transactional(rollbackFor = Exception.class)
 *                发生异常就回滚
 * @author      : 梁山广
 * @date        : 2017/12/27 19:06
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentListByBlogId(Long blogId) {
        // 根据更新时间进行评论排序
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return commentRepository.findByBlogId(blogId, sort);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        // 如果不是根评论的话,-1是在前端规定好的
        if (parentCommentId != -1) {
            // 设置父评论
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        } else {
            // 如果本身就是根评论的话,那么父评论一定要设置为null,要不就乱套了,容易形成循环
            comment.setParentComment(null);
        }
        // 设置评论时间戳
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
