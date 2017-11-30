package com.huawei.l00379880.myblogbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/***********************************************************
 * @Description : 博客未发现的异常类,继承的时候要继承具体的Exception
 * @author      : 梁山广
 * @date        : 2017/11/30 20:52
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
