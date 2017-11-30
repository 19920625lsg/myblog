package com.huawei.l00379880.myblogbackend.controller;

import com.huawei.l00379880.myblogbackend.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    // 常规操作,返回SpringBoot官方的处理,这里是NOT_FOUND即404错误
    @GetMapping("/notfindTest")
    String notfindTest() {
        String blog=null;
        if (blog==null){
            throw  new NotFoundException("异常:博客找不到...");
        }
        return "index";
    }

    @GetMapping("/LogAspectTest/{id}/{name}")
    String logAspectTest(@PathVariable Integer id,@PathVariable String name) {

        System.out.println("------------------in LogAspectTest--------------------");
        return "index";
    }

}
