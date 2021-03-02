package com.blog.service;


import com.blog.model.Blog;
import com.blog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBlogService {




    Blog getBlog(Long id);


    Page<Blog> listBlog(Pageable pageable , BlogQuery blog );

    Page<Blog> listBlog(Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id , Blog blog) throws NotFoundException;

    void deleteBlog(Long id) throws NotFoundException;












}
