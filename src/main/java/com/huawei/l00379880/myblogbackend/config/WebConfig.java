package com.huawei.l00379880.myblogbackend.config;

import com.huawei.l00379880.myblogbackend.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/***********************************************************
 * @Description : 拦截规则配置器,用于拦截非法后台访问
 * @author      : 梁山广
 * @date        : 2017/12/13 19:15
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截admin下的所有非法请求,放行正常的登录
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
