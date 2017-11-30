package com.huawei.l00379880.myblogbackend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/***********************************************************
 * @Description : 拦截所有Controller中出现的异常
 * @author      : 梁山广
 * @date        : 2017/11/30 20:25
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    ModelAndView execptionHandler(HttpServletRequest request, Exception e) {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURI(), e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        // 待返回的页面,以templates作为根路径
        mv.setViewName("error/error");
        return mv;
    }
}
