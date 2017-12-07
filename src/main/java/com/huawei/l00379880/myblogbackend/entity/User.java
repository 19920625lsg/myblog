package com.huawei.l00379880.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***********************************************************
 * @Description : 用户类(访客和自己)
 * @author      : 梁山广
 * @date        : 2017/12/6 20:25
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Entity
@Table(name = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    /**
     * 用户类型,用于权限划分地
     */
    private Integer type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 一个用户对应多个博客
     */
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();
}
