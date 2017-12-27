package com.huawei.l00379880.myblogbackend.service.impl;

import com.huawei.l00379880.myblogbackend.entity.Comment;
import com.huawei.l00379880.myblogbackend.repository.CommentRepository;
import com.huawei.l00379880.myblogbackend.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        // 根据更新时间进行评论排序,越晚的评论越靠后
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        List<Comment> commentList = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        // 只拿到顶级评论,子级评论一律按照二级来,设置进顶级评论对象中
        return eachComment(commentList);
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

    @Override
    public String getAvatar(String nickname, String email) {
        List<Comment> commentList = commentRepository.findByNicknameAndEmail(nickname, email);
        // 如果查询到此用户的昵称就返回这个用户之前的头像,否则就生成一个
        if (commentList.size() == 0) {
            // 该昵称的评论之前不存在
            // 生成[0,10]之间的整数,然后我们自己组成avatar的路径,随机选一个头像
            int avatarNum = new Random().nextInt(15);
            // 随机选一个static/images/avatar/路径下的一个图片,作为头像
            String avatarStr = "/images/avatar/avatar" + avatarNum + ".jpg";
            return avatarStr;
        } else {
            return commentList.get(0).getAvatar();
        }
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 合并各个子级的评论
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 存放迭代找出的所有子代的集合
     */
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        // 顶节点添加到临时存放集合
        tempReplys.add(comment);
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
