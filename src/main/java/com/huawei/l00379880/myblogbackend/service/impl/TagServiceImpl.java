package com.huawei.l00379880.myblogbackend.service.impl;

import com.huawei.l00379880.myblogbackend.entity.Tag;
import com.huawei.l00379880.myblogbackend.exception.NotFoundException;
import com.huawei.l00379880.myblogbackend.repository.TagRepository;
import com.huawei.l00379880.myblogbackend.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***********************************************************
 * @Description : 博客标签服务实现,对所有方法开启事物
 * @author      : 梁山广
 * @date        : 2017/12/13 20:24
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        // 先查询是不是有这个类型
        Tag t = tagRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该博客标签");
        }
        BeanUtils.copyProperties(tag, t);
        // 自动根据id去更新
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }
}
