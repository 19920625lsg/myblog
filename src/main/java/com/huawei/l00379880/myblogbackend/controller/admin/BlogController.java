package com.huawei.l00379880.myblogbackend.controller.admin;

import com.huawei.l00379880.myblogbackend.entity.Blog;
import com.huawei.l00379880.myblogbackend.entity.User;
import com.huawei.l00379880.myblogbackend.service.BlogService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/***********************************************************
 * @Description : 后台博客编辑和访问
 * @author      : 梁山广
 * @date        : 2017/12/13 18:41
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String BLOG_LIST = "admin/blogs";
    private static final String BLOG_LIST_REDIRECT = "redirect:/admin/blogs";
    private static final String BLOG_ADD = "admin/blog-add";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 访问后台博客列表,但是需要拦截器的哦,必须登录的用户才能访问博客列表
     *
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return BLOG_LIST;
    }


    @PostMapping("/blogs/search")
    public String blogsSearch(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogAdd")
    public String blogAdd(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return BLOG_ADD;
    }

    @PostMapping("/blogAdd")
    public String blogSave(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            // 未保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "新增or修改博客失败！");
        } else {
            // 保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "修改or修改博客成功！");
        }
        return BLOG_LIST_REDIRECT;
    }

}
