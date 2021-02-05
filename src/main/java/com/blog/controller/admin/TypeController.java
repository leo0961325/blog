package com.blog.controller.admin;


import com.blog.model.Type;
import com.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    ITypeService iTypeService;


    @GetMapping("/types")
    //Pageable選domin的
    //一頁10筆; 以Id做排序 ; direction並以降冪的方式呈現
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){

        //是Page物件
        model.addAttribute("page",iTypeService.listType(pageable));


        return "admin/types";
    }












}
