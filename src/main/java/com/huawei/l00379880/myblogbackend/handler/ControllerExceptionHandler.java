package com.huawei.l00379880.myblogbackend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    ModelAndView execptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURI(), e);
        // 如果是常规的异常类型就交给SpringBoot.不常规的才交给自定义的Controller去处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }
        // 非常规操作,自定义处理
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        // 待返回的页面,以templates作为根路径
        mv.setViewName("error/error");
        return mv;
    }
}
