package com.huawei.l00379880.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 文章标签<=====>文章,多对多
 * @author      : 梁山广
 * @date        : 2017/12/6 20:11
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 分类名称
     */
    private String name;

    /**
     * 由标签类来维护多对多的关系
     */
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();
}
