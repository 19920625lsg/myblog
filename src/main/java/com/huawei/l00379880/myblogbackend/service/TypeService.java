package com.huawei.l00379880.myblogbackend.service;

import com.huawei.l00379880.myblogbackend.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/***********************************************************
 * @Description : 博客类型服务接口
 * @author      : 梁山广
 * @date        : 2017/12/13 20:23
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface TypeService {
    /**
     * 保存分类
     *
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 根据id查询分类
     *
     * @param id 分类的id
     * @return
     */
    Type getType(Long id);

    Type getTypeByName(String name);

    /**
     * 分页查询
     *
     * @param pageable 分页配置对象,可传入size(每页条数)、count(第几页)、sort(排序方法)
     * @return
     */
    Page<Type> listType(Pageable pageable);


    /**
     * 获取所有分类
     *
     * @return
     */
    List<Type> listType();

    /**
     * 获取前size个分类
     *
     * @param size
     * @return
     */
    List<Type> listTypeTop(Integer size);

    /**
     * 更新分类
     *
     * @param id   由id查询分类
     * @param type 更改过后的分类
     * @return
     */
    Type updateType(Long id, Type type);

    /**
     * 根据逐渐来删除分类
     *
     * @param id 待删除分类的id
     */
    void deleteType(Long id);

    /**
     * 查询所有的分类
     *
     * @return 所有的分类
     */
    List<Type> findAll();
}
