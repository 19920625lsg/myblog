package com.huawei.l00379880.myblogbackend.service.impl;

import com.huawei.l00379880.myblogbackend.entity.User;
import com.huawei.l00379880.myblogbackend.repository.UserRepository;
import com.huawei.l00379880.myblogbackend.service.UserService;
import com.huawei.l00379880.myblogbackend.service.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***********************************************************
 * @Description : 用户接口实现类
 * @author      : 梁山广
 * @date        : 2017/12/7 21:13
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Util.getMD5(password));
    }
}
