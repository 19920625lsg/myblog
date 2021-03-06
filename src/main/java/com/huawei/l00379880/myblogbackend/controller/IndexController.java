package com.huawei.l00379880.myblogbackend.controller;

import com.huawei.l00379880.myblogbackend.entity.Tag;
import com.huawei.l00379880.myblogbackend.entity.Type;
import com.huawei.l00379880.myblogbackend.exception.NotFoundException;
import com.huawei.l00379880.myblogbackend.service.BlogService;
import com.huawei.l00379880.myblogbackend.service.CommentService;
import com.huawei.l00379880.myblogbackend.service.TagService;
import com.huawei.l00379880.myblogbackend.service.TypeService;
import com.huawei.l00379880.myblogbackend.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/***********************************************************
 * @Description : 配置各个模块的访问路由
 * @author      : 梁山广
 * @date        : 2017/11/30 20:14
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/blog/{id}")
    String blog(@PathVariable Long id, Model model) {
        // 在getAndConvertBlog方法中实现访问量自增了
        model.addAttribute("blog", blogService.getAndConvertBlog(id));
        // 获取指定博客下的评论列表
        model.addAttribute("comments", commentService.getCommentListByBlogId(id));
        return "blog";
    }

    /**
     * 前端的博客、分类、标签、博客推荐等的model返回
     *
     * @param model 返回给前端的Model
     */
    private void addModel(Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        // 取前8个分类,按照该分类下的博客的数据进行排序,越考上的分类博客数越多
        model.addAttribute("types", typeService.listTypeTop(10));
        model.addAttribute("tags", tagService.listTagTop(30));
        // 获取8个推荐博客
        model.addAttribute("recommendedBlogs", blogService.listTopRecommendedBlog(10));
    }

    @GetMapping("/")
    String index(@PageableDefault(size = 6, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        addModel(pageable, model);
        return "index";
    }

    @GetMapping("/index")
    String index2(@PageableDefault(size = 6, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        addModel(pageable, model);
        return "index";
    }

    @GetMapping("/search")
    String search(@PageableDefault(size = 6, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog(query, pageable));
        model.addAttribute("query", query);
        return "search";
    }

    /**
     * @param id 活跃的分类的ID
     * @return 返回到分类页面
     */
    @GetMapping("/types/{id}")
    String types(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                 @PathVariable Long id, Model model) {
        List<Type> typesTemp = typeService.findAll();
        // 之所以再取一遍是为了按照blogs的数目,见listTypeTop的实现.findAll()直接传入sort对象会报找不到blogs.size属性
        List<Type> types = typeService.listTypeTop(typesTemp.size());
        model.addAttribute("types", types);
        if (id == -1) {
            // 说明是从导航栏直接点击过来地.那么就把第一个分类作为活跃分类
            id = types.get(0).getId();
        }
        // 根据typeId进行下面所属博客数目的查询
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        // 返回当前活跃的typeId
        model.addAttribute("activeTypeId", id);
        return "types";
    }

    @GetMapping("/tags/{id}")
    String tags(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                @PathVariable Long id, Model model) {
        List<Tag> tagsTemp = tagService.findAll();
        // 之所以再取一遍是为了按照blogs的数目,见listTagTop的实现.findAll()直接传入sort对象会报找不到blogs.size属性
        List<Tag> tags = tagService.listTagTop(tagsTemp.size());
        model.addAttribute("tags", tags);
        if (id == -1) {
            // 说明是从导航栏直接点击过来地.那么就把第一个标签作为活跃分类
            id = tags.get(0).getId();
        }
        model.addAttribute("page", blogService.listBlog(pageable, id));
        // 返回当前活跃的typeId
        model.addAttribute("activeTagId", id);
        return "tags";
    }

    @GetMapping("/archives")
    String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }

    @GetMapping("/about")
    String about() {
        return "about";
    }

}
