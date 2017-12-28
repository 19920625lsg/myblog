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

import java.util.List;
import java.util.Map;

public interface BlogService {

    /**
     * 根据id获取博客
     *
     * @param id 博客主键
     * @return
     */
    Blog getBlog(Long id);

    /**
     * 根据ID获取博客并进行markdown转换
     *
     * @param id 博客主键
     * @return
     */
    Blog getAndConvertBlog(Long id);

    /**
     * 博客管理页,根据BlogQuery里的查询条件进行分页查询
     *
     * @param pageable 分页对象
     * @param blog     查询条件,见实体对象
     * @return
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    /**
     * 博客分页获取
     *
     * @param pageable 分页对象
     * @return
     */
    Page<Blog> listBlog(Pageable pageable);

    /**
     * 根据标签id查询下面的所有博客
     */
    Page<Blog> listBlog(Pageable pageable, Long tagId);

    /**
     * 搜索根据关键词进行分页查询,查询标题(title)、描述(description)、博客内容(content)是不是包含关键词
     *
     * @param query    查询关键词
     * @param pageable 分页对象
     * @return
     */
    Page<Blog> listBlog(String query, Pageable pageable);

    /**
     * 获取前size条博客,首先是推荐的,其次是访问量
     *
     * @param size 推荐的博客
     * @return
     */
    List<Blog> listTopRecommendedBlog(Integer size);

    /**
     * 保存博客
     *
     * @param blog 博客对象
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 更新博客
     *
     * @param id   博客Id
     * @param blog 待保存博客对象
     * @return
     */
    Blog updateBlog(Long id, Blog blog);

    /**
     * 根据id删除博客
     *
     * @param id 博客id
     */
    void deleteBlog(Long id);

    /**
     * 获取每个年份下的博客列表,以<年份,博客列表>的形式返回
     *
     * @return <年份,博客列表>的map
     */
    Map<String, List<Blog>> archiveBlog();

    /**
     * 获得博客总数
     */
    Long countBlog();
}
