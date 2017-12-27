/***********************************************************
 * @Description : 
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2017/12/17 下午8:24
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package com.huawei.l00379880.myblogbackend.service.impl;

import com.huawei.l00379880.myblogbackend.entity.Blog;
import com.huawei.l00379880.myblogbackend.entity.Type;
import com.huawei.l00379880.myblogbackend.exception.NotFoundException;
import com.huawei.l00379880.myblogbackend.repository.BlogRepository;
import com.huawei.l00379880.myblogbackend.service.BlogService;
import com.huawei.l00379880.myblogbackend.service.util.MarkdownUtil;
import com.huawei.l00379880.myblogbackend.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public Blog getAndConvertBlog(Long id) {
        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            throw new NotFoundException("博客未找到");
        }
        // 找到博客，则对对应id的博客的访问量加1
        blogRepository.updateVisits(id);
        // 之所以要新建blog对象是因为直接在原blog对象上修改的话会因为hibernate session的问题导致content被更新到数据库中，
        // 成为html的内容
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtil.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 下面为条件的动态组合
                List<Predicate> predicateList = new ArrayList<>();
                // 博客标题不为空和Null
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                // 分类不为空的话
                if (blog.getTypeId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.<Type>get("type"), blog.getTypeId()));
                }
                // 是否被推荐
                if (blog.isRecommended()) {
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommended"), blog.isRecommended()));
                }
                // 组合上面的查询条件，不为空就会被加入到下面的条件中
                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        // 加上两个%是为了进行模糊查询
        return blogRepository.findByQuery("%" + query + "%", pageable);
    }

    @Override
    public List<Blog> listTopRecommendedBlog(Integer size) {
        // 按照该分类下的博客的更新事件进行排序.已经在repository中选取访问量最大的几个博客了
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findTopRecommended(pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        // 博客首次提交的id为null
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setVisits(0);
        } else {
            // 这种情况为保存,所以之前的createTime因为不能再前端展示,所以这里获取一下数据库现有地
            blog.setCreateTime(blogRepository.getOne(blog.getId()).getCreateTime());
        }
        // 更新每次都刷新时间
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if (b == null) {
            throw new NotFoundException("该博客未找到！！");
        }
        BeanUtils.copyProperties(blog, b);
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }
}
