package com.huawei.l00379880.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/***********************************************************
 * @Description : 评论类(评论之间也有对应关系)
 * @author      : 梁山广
 * @date        : 2017/12/6 20:17
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Entity
@Table(name = "t_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String avatar;
    /**
     * 用户头像,为一个URL地址
     */
    @Temporal(TemporalType.TIMESTAMP)
    /**
     * 评论创建时间(不可更新评论,所以没有updateTime字段)
     */
    private String createTime;

    /**
     * 多个评论对应一个博客
     */
    @ManyToOne
    private Blog blog;
}
