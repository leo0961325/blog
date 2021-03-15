package com.blog.controller;


import com.blog.model.Comment;
import com.blog.service.IBlogService;
import com.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {


    @Autowired
    ICommentService iCommentService;

    @Autowired
    IBlogService iBlogService;

    //先把大頭貼在property寫死
    @Value("${comment.avatar}")
    private String avatar;


    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){

        model.addAttribute("comments" , iCommentService.listCommentByBlogId(blogId));

        //返回Blog頁面下的commentList
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment){

        Long blogId = comment.getBlog().getId();

        comment.setBlog(iBlogService.getBlog(blogId));
        comment.setAvatar(avatar);

        iCommentService.saveComment(comment);

        return "redirect:/comments/" + blogId ;
    }








}
