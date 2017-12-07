package com.huawei.l00379880.myblogbackend.repository;

import com.huawei.l00379880.myblogbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/***********************************************************
 * @Description : 用户数据表的操作类
 * @author      : 梁山广
 * @date        : 2017/12/7 21:15
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
