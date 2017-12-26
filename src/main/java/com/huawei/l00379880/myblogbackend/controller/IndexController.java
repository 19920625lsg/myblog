package com.huawei.l00379880.myblogbackend.controller;

import com.huawei.l00379880.myblogbackend.exception.NotFoundException;
import com.huawei.l00379880.myblogbackend.service.BlogService;
import com.huawei.l00379880.myblogbackend.service.TagService;
import com.huawei.l00379880.myblogbackend.service.TypeService;
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

    @GetMapping("/about")
    String about() {
        return "about";
    }

    @GetMapping("/archives")
    String archives() {
        return "archives";
    }

    @GetMapping("/blog/{id}")
    String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getBlog(id));
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
        model.addAttribute("types", typeService.listTypeTop(8));
        model.addAttribute("tags", tagService.listTagTop(15));
        // 获取8个推荐博客
        model.addAttribute("recommendedBlogs", blogService.listTopRecommendedBlog(5));
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

    @GetMapping("/tags")
    String tags() {
        return "tags";
    }

    @GetMapping("/types")
    String types() {
        return "types";
    }

    @GetMapping("/search")
    String search(@PageableDefault(size = 6, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog(query, pageable));
        model.addAttribute("query", query);
        return "search";
    }


    /**
     * 被0除的非常规的异常,所以根据ControllerExceptionHandler可以要跳转到error.html页面
     */
    @GetMapping("/errorTest")
    String errorTest() {
        int i = 9 / 0;
        return "index";
    }

    /**
     * 常规操作,返回SpringBoot官方的处理,这里是NOT_FOUND即404错误(在类NotFoundException中有)
     */
    @GetMapping("/notfindTest")
    String notfindTest() {
        String blog = null;
        if (blog == null) {
            throw new NotFoundException("异常:博客找不到...");
        }
        return "index";
    }

    @GetMapping("/LogAspectTest/{id}/{name}")
    String logAspectTest(@PathVariable Integer id, @PathVariable String name) {

        System.out.println("------------------in LogAspectTest--------------------");
        return "index";
    }

}
