package com.huawei.l00379880.myblogbackend.repository;

import com.huawei.l00379880.myblogbackend.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/***********************************************************
 * @Description : 博客分类数据表的操作类
 * @author      : 梁山广
 * @date        : 2017/12/7 21:15
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface TypeRepository extends JpaRepository<Type, Long> {

}
