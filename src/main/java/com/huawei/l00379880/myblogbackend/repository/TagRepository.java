package com.huawei.l00379880.myblogbackend.repository;

import com.huawei.l00379880.myblogbackend.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/***********************************************************
 * @Description : 博客标签数据表的操作类
 * @author      : 梁山广
 * @date        : 2017/12/7 21:15
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据标签名查询标签
     *
     * @param name 标签名
     * @return
     */
    Tag findByName(String name);

    /**
     * 选取指定数目的前几个标签
     *
     * @param pageable 分页对象
     * @return
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
