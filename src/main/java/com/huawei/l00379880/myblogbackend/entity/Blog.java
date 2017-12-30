package com.huawei.l00379880.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***********************************************************
 * @Description : 博客实体类
 * @author      : 梁山广
 * @date        : 2017/12/6 19:34
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容,对应LongText数据类型,要不String 类型默认为varchar(255),太短了
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    /**
     * 文章首图
     */
    private String firstPicture;
    /**
     * 是否显示首图
     */
    private boolean firstPictureShow;
    /**
     * 标记
     */
    private String flag;
    /**
     * 浏览量
     */
    private Integer visits;
    /**
     * 是否开启赞赏
     */
    private boolean appreciation;
    /**
     * 转载声明是否开启(版权声明)
     */
    private boolean shareStatement;
    /**
     * 是否允许评论
     */
    private boolean commendable;
    /**
     * 发布还是保存为草稿
     */
    private boolean published;
    /**
     * 是否推荐
     */
    private boolean recommended;

    /**
     * 这个字段不需要保存到数据库中,对应前端的tagIds标签
     */
    @Transient
    private String tagIds;

    /**
     * 博客描述
     */
    private String description;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 博客只能属于一个分类,一个分类有多个博客
     */
    @ManyToOne
    private Type type;

    /**
     * 博客<===>标签 多对多
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    /**
     * 多个blog对应一个user
     */
    @ManyToOne
    private User user;

    /**
     * 一个博客对应多个评论,一对多的时候由"一"维护.多对多二选一即可.一对一一般不用
     */
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
}
