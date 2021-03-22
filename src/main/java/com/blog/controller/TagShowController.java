package com.blog.controller;

import com.blog.model.Tag;
import com.blog.service.IBlogService;
import com.blog.service.ITagService;
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
public class TagShowController {

    @Autowired
    ITagService iTagService;

    @Autowired
    IBlogService iBlogService;

    //當前分類頁面選中的第一個
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model) {

        List<Tag> tags = iTagService.listTagTop(10000);

        //從導航點進來 id = -1
        if (id == -1) {
            id = tags.get(0).getId();
        }

        model.addAttribute("tags", tags);
        model.addAttribute("page", iBlogService.listBlog(id,pageable));
        //點擊的標籤傳到前台
        model.addAttribute("activeTagId", id);

        return "tags";
    }


}
