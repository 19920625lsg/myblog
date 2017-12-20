package com.huawei.l00379880.myblogbackend.service;

import com.huawei.l00379880.myblogbackend.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/***********************************************************
 * @Description : 博客标签服务接口
 * @author      : 梁山广
 * @date        : 2017/12/13 20:23
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public interface TagService {
    /**
     * 保存标签
     *
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 根据id查询标签
     *
     * @param id 标签的id
     * @return
     */
    Tag getTag(Long id);

    Tag getTagByName(String name);

    /**
     * 分页查询
     *
     * @param pageable 分页配置对象,可传入size(每页条数)、count(第几页)、sort(排序方法)
     * @return
     */
    Page<Tag> listTag(Pageable pageable);

    /**
     * 获取所有的标签
     *
     * @return
     */
    List<Tag> listTag();

    /**
     * 更新标签
     *
     * @param id  由id查询标签
     * @param tag 更改过后的标签
     * @return
     */
    Tag updateTag(Long id, Tag tag);

    /**
     * 根据逐渐来删除标签
     *
     * @param id 待删除标签的id
     */
    void deleteTag(Long id);
}
