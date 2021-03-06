package com.huawei.l00379880.myblogbackend.service.impl;

import com.huawei.l00379880.myblogbackend.entity.Type;
import com.huawei.l00379880.myblogbackend.exception.NotFoundException;
import com.huawei.l00379880.myblogbackend.repository.TypeRepository;
import com.huawei.l00379880.myblogbackend.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***********************************************************
 * @Description : 博客分类服务实现,对所有方法开启事物
 * @author      : 梁山广
 * @date        : 2017/12/13 20:24
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        // 按照该分类下的博客的数据进行排序,越考上的分类博客数越多
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    @Override
    public Type updateType(Long id, Type type) {
        // 先查询是不是有这个类型
        Type t = typeRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该博客分类");
        }
        BeanUtils.copyProperties(type, t);
        // 自动根据id去更新
        return typeRepository.save(t);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }
}
