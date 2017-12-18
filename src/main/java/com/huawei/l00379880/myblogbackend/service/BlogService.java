/***********************************************************
 * @Description : 博客管理的服务类
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2017/12/17 下午8:19
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package com.huawei.l00379880.myblogbackend.service;

import com.huawei.l00379880.myblogbackend.entity.Blog;
import com.huawei.l00379880.myblogbackend.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
