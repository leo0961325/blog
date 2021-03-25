package com.blog.controller;

import com.blog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.OrderBy;

@Controller
public class ArchiveShowController {


    @Autowired
    IBlogService iBlogService;



    @GetMapping("/archives")
    public String archives(Model model){

        model.addAttribute("archiveMap" ,iBlogService.archiveBlog());
        model.addAttribute("blogCount",iBlogService.countBlog());


        return "archives";
    }








}
