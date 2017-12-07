package com.huawei.l00379880.myblogbackend.service;

import com.huawei.l00379880.myblogbackend.entity.User;

/***********************************************************
 * @Description : 用户管理接口
 * @author      : 梁山广
 * @date        : 2017/12/7 21:10
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface UserService {
    User validateUser(String username,String password);
}
