package com.huawei.l00379880.myblogbackend.repository;

import com.huawei.l00379880.myblogbackend.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***********************************************************
 * @Description : Comment的数据库操作类
 * @author      : 梁山广
 * @date        : 2017/12/27 19:09
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBlogId(Long blodId, Sort sort);
}
