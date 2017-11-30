package com.huawei.l00379880.myblogbackend.controller;

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
}
