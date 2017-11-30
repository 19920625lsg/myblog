package com.huawei.l00379880.myblogbackend.controller;

import com.huawei.l00379880.myblogbackend.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***********************************************************
 * @Description : 首页相关的Controller
 * @author      : 梁山广
 * @date        : 2017/11/30 20:14
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
public class IndexController {
    @GetMapping("/")
    String index() {
        int i = 9 / 0;
        return "index";
    }

    // 被0除的异常非常规的异常,所以根据ControllerExceptionHandler可以要跳转到error.html页面
    @GetMapping("/errorTest")
    String errorTest() {
        int i = 9 / 0;
        return "index";
    }

    // 常规操作,返回SpringBoot官方的处理
    @GetMapping("/notfindTest")
    String notfindTest() {
        String blog=null;
        if (blog==null){
            throw  new NotFoundException("异常:博客找不到...");
        }
        return "index";
    }

}
