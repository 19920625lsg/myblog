package com.huawei.l00379880.myblogbackend.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/***********************************************************
 * @Description : 后台博客编辑和访问
 * @author      : 梁山广
 * @date        : 2017/12/13 18:41
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
@RequestMapping("/admin")
public class BlogController {

    /**
     * 访问后台博客列表,但是需要拦截器的哦,必须登录的用户才能访问博客列表
     * @return
     */
    @GetMapping("/blogs")
    public String blogs() {
        return "admin/blogs";
    }

    @GetMapping("/blogAdd")
    public String blogAdd() {
        return "admin/blog-add";
    }

}
