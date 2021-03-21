package com.blog.controller;

import com.blog.model.Type;
import com.blog.service.IBlogService;
import com.blog.service.IMP.BlogService;
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

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    ITypeService iTypeService;

    @Autowired
    IBlogService iBlogService;

    //當前分類頁面選中的第一個
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id , Model model){

        List<Type> types = iTypeService.listTypeTop(10000);

        //從導航點進來 id = -1
        if (id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);

        model.addAttribute("types" , types);
        model.addAttribute("page" ,iBlogService.listBlog(pageable , blogQuery));
        //點擊的標籤傳到前台
        model.addAttribute("activeTypeId" ,id);

        return "types";
    }













}
