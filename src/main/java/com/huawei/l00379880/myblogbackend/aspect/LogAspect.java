/***********************************************************
 * @Description : 日志记录拦截器，记录请求的url、地址、
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2017/11/30 下午11:44
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package com.huawei.l00379880.myblogbackend.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截控制器中的所有方法
     */
    @Pointcut("execution(* com.huawei.l00379880.myblogbackend.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取url和ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        // 获取方法和对象
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        // 给各个参数赋值
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);

        logger.info("--------------------------------doBefore:下面是请求方的所有信息  -----------------------------------");
        logger.info(requestLog.toString());
    }

    @After("log() ")
    public void doAfter() {
        logger.info("---------------------------------doAfter------------------------------------");
    }

    /**
     * 获取返回值
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        logger.info("返回值为 ={}", result);
    }

    @Data
    @AllArgsConstructor
    private class RequestLog {
        /**
         * 接口地址
         */
        private String url;
        /**
         * 访问者的IP
         */
        private String ip;
        /**
         * 调用的累和方法
         */
        private String classMethod;
        /**
         * 传入的参数
         */
        private Object[] args;

    }
}
