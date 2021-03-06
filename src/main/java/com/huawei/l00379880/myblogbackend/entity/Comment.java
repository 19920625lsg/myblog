package com.huawei.l00379880.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***********************************************************
 * @Description : 评论类(评论之间也有对应关系)
 * @author      : 梁山广
 * @date        : 2017/12/6 20:17
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 用户(访客和自己)昵称
     */
    private String nickname;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 用户头像,为一个URL地址
     */
    private String avatar;
    /**
     * 评论创建时间(不可更新评论,所以没有updateTime字段)
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 多个评论对应一个博客
     */
    @ManyToOne
    private Blog blog;

    /**
     * ******************* 自关联,评论可以互相回复,即有内楼.******************************
     * <p>
     * 一个评论下面有多条回复(即子评论呢)
     */
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    /**
     * 一个子评论只有一个父评论
     */
    @ManyToOne
    private Comment parentComment;

    /**
     * 是否是管理员，这个标志位不存到数据库中
     */
    private boolean admin;
}
