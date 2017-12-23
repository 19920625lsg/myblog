/***********************************************************
 * @Description : 
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2017/12/17 下午8:25
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package com.huawei.l00379880.myblogbackend.repository;

import com.huawei.l00379880.myblogbackend.entity.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommended = true order by b.visits")
    List<Blog> findTopRecommended(Pageable pageable);
}
