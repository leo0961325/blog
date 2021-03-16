package com.blog.repository;


import com.blog.model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment , Long> {

    //findByBlogIdAndParentCommentNot JPA提供方法，只找父級為空(即為頂級節點)
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId , Sort sort);





}
