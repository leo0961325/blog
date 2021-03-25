package com.blog.repository;


import com.blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderBy;
import java.util.List;

/**
 *  JpaSpecificationExecutor動態查詢組合
 */
public interface IBlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {





        //查詢有推薦的
        @Query("SELECT b FROM Blog b WHERE b.recommend = true ")
        List<Blog> findTop(Pageable pageable);

        //全域搜索查詢
        //包含 1.標題 2.文章內容 3.表頭敘述等
        @Query("SELECT b FROM Blog b WHERE b.title LIKE ?1 OR b.content LIKE ?1 OR b.description LIKE ?1")
        Page<Blog> findByQuery(String query,Pageable pageable);

        //更新瀏覽人數使用，每刷新訪問一次就+1
        @Transactional
        @Modifying
        @Query("UPDATE Blog b SET b.views = b.views + 1 WHERE b.id = ?1 ")
        int updateViews(Long id);

        //查詢所有年分，並GroupBY，使用JPQL的function
        @Query("SELECT function('date_format',b.updateTime,'%Y') AS year FROM Blog b  GROUP BY year ORDER BY b.updateTime")
        List<String> findGroupYear();

        @Query("SELECT b FROM Blog b WHERE function('date_format' ,b.updateTime,'%Y' ) = ?1")
        List<Blog> findByYear(String year);
}
