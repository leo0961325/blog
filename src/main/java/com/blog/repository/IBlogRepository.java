package com.blog.repository;


import com.blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *  JpaSpecificationExecutor動態查詢組合
 */
public interface IBlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {





        //查詢有推薦的
        @Query("SELECT b FROM Blog b WHERE b.recommend = true ")
        List<Blog> findTop(Pageable pageable);

        //全域搜索查詢
        @Query("SELECT b FROM Blog b WHERE b.title LIKE ?1 or b.content LIKE ?1")
        Page<Blog> findByQuery(String query,Pageable pageable);











}
