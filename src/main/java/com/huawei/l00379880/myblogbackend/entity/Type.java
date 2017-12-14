package com.huawei.l00379880.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 文章类型<=====文章  一对多
 * @author      : 梁山广
 * @date        : 2017/12/6 20:11
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 分类名称
     */
    @NotBlank(message = "后端校验：分类名称不能为空")
    private String name;

    /**
     * 一个分类下有多个博客.mappedBy根据blog中的type属性来绑定
     */
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();
}
