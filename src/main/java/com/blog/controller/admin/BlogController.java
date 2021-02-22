package com.blog.controller.admin;


import com.blog.model.Blog;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";




    @Autowired
    IBlogService iBlogService;

    @Autowired
    ITypeService iTypeService;

    @Autowired
    ITagService iTagService;

    //@PageableDefault 一頁筆數;根據更新時間,排序方式->降冪
    //改BlogQuery 2/20
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        //渲染Type到前端去
        model.addAttribute("types",iTypeService.listType());
        model.addAttribute("page", iBlogService.listBlog(pageable, blog));

        return LIST;
    }

    /**
     * 搜索 ->局部刷新使用
     *
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {

        model.addAttribute("page", iBlogService.listBlog(pageable, blog));
        //blogList 為 blogsAdmin底下的片段
        return REDIRECT_LIST;


    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        //初始化
        model.addAttribute("types",iTypeService.listType());
        model.addAttribute("tags",iTagService.listTag());
        model.addAttribute("blog" , new Blog());


        return INPUT;
    }

}
