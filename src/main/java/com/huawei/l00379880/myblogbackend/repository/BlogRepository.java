/***********************************************************
 * @Description : 
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2017/12/17 下午8:25
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package com.huawei.l00379880.myblogbackend.repository;

import com.huawei.l00379880.myblogbackend.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * 按照访问数进行博客推荐
     */
    @Query("select b from Blog b where b.recommended = true order by b.visits desc")
    List<Blog> findTopRecommended(Pageable pageable);

    /**
     * 根据查询对象和分页信息获取博客
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    /**
     * 修改博客访问量，记得务必加Modifying
     */
    @Modifying
    @Query("update Blog b set b.visits = b.visits + 1 where b.id = ?1")
    int updateVisits(Long id);

    /**
     * 找到所有的年份
     *
     * @return 所有年份的字符串
     */
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    /**
     * 根据年份插叙该年的所有博客，并按照博客的更新时间降序排列
     *
     * @param year 年份
     * @return 该年内的所有博客，按照更新时间进行降序排列
     */
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 order by b.updateTime desc")
    List<Blog> findByYear(String year);
}
