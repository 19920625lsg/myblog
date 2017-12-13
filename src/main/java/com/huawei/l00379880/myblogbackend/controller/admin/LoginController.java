package com.huawei.l00379880.myblogbackend.controller.admin;

import com.huawei.l00379880.myblogbackend.entity.User;
import com.huawei.l00379880.myblogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/***********************************************************
 * @Description : 登陆控制器
 * @author      : 梁山广
 * @date        : 2017/12/7 21:22
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage1(HttpSession session) {
        // 如果用户已经登录过的话就直接跳到主页
        if(session.getAttribute("user")!=null){
            return "admin/index";
        }
        return "admin/login";
    }

    @GetMapping("/login")
    public String loginPage2(HttpSession session) {
        // 如果用户已经登录过的话就直接跳到主页
        if(session.getAttribute("user")!=null){
            return "admin/index";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes) {
        User user = userService.validateUser(username, password);
        if (user != null) {
            // 用户不为空,要把用户信息放到Sesson中,但是不要把用户密码放到session中
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            // 登陆失败地话就重定向到登录页,并传给前端错误消息
            attributes.addFlashAttribute("message", "用户名或密码错误!");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 用户注销,要清空session,然后重定向到登录页
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
