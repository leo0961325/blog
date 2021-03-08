package com.blog.controller;


import com.blog.exception.NotFoundException;
import com.blog.service.IBlogService;
import com.blog.service.ITagService;
import com.blog.service.ITypeService;
import com.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Create By Harvey 2021/01/13
 * 要加thymeleaf-3.0.9那包
 * pom <property> </property> 不用再加themeleaf的怪東西
 */

@Controller
public class IndexController {

    @Autowired
    IBlogService iBlogService;

    @Autowired
    ITypeService iTypeService;

    @Autowired
    ITagService iTagService;

    @GetMapping(value = "/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {


        model.addAttribute("page" , iBlogService.listBlog(pageable));
        model.addAttribute("types",iTypeService.listTypeTop(6));
        model.addAttribute("tags",iTagService.listTagTop(10));
        model.addAttribute("recommendBlogs" , iBlogService.listRecommendBlog(8));


        System.out.println("------------index-----------");
        //如果是資料夾下要在加 dir/index
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query , Model model){

        //像是SQL語句 %LIKE% 的查詢方法
        model.addAttribute("page",iBlogService.listBlog("%"+query+"%",pageable));
        //將查詢字符串返回頁面上去
        model.addAttribute("query",query);

        return "search";
    }


    @GetMapping(value = "/blog/{id}")
    public String blog() {

        //錯誤的範例使用
        //int x = 9/0;

//        String blog = null;
//        if (blog == null){
//
//            throw new NotFoundException("Blog not exist");
//        }

        return "blog";
    }


}
