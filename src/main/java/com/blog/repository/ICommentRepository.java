package com.blog.repository;


import com.blog.model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment , Long> {


    List<Comment> findByBlogId(Long blogId , Sort sort);





}
