package com.huawei.l00379880.myblogbackend.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***********************************************************
 * @Description : 登录拦截器,拦截用户对后台页面的无登录访问
 * @author      : 梁山广
 * @date        : 2017/12/13 18:57
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 如果用户没登录,就返回false,无法执行下面的访问后台页面的请求了
        if (request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
