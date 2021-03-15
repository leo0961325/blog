package com.blog.service;

import com.blog.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICommentService {




    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);










}
