package com.blog.controller.admin;

import com.blog.model.Tag;
import com.blog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;



@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private ITagService itagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        //是Page物件
        model.addAttribute("page",itagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 返回新增"標籤"頁面
     * @return
     */

    @GetMapping("/tags/input")
    public String input(Model model) {
        //傳遞object到tags-input.html
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", itagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 增加分類名稱
     * @Valid署名要校驗 NotBlank
     * BindingResult 接收校驗後結果使用
     * @param tag
     * @param attributes
     * @return
     */
    //Tag BindingResult 兩個參數是綁在一起的不然會無效

    @PostMapping("/tags")
    public String post(@Valid Tag tag,BindingResult result, RedirectAttributes attributes) {
        Tag tagFindByname = itagService.getTagByName(tag.getName());
        if (tagFindByname != null) {
            //arg1 name和Type.name保持一致為要校驗對象
            //arg2 驗證代碼
            //arg3 返回的消息
            result.rejectValue("name","nameError","不能添加重複標籤");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tagSave = itagService.saveTag(tag);
        if (tagSave == null ) {
            //操作消息顯示RedirectAttributes
            //渲染到types的message search:提示
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        //重定向 -> 因為沒有經過樓上的types去查詢拿不到物件所以必須重定向
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tagFindByName = itagService.getTagByName(tag.getName());
        if (tagFindByName != null) {
            result.rejectValue("name","nameError","不能添加重复的標籤");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tagSave = itagService.updateTag(id,tag);
        if (tagSave == null ) {
            //操作消息顯示RedirectAttributes
            //渲染到types的message search:提示
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        //重定向 -> 因為沒有經過樓上的types去查詢拿不到物件所以必須重定向
        return "redirect:/admin/tags";
    }


    /**
     * 刪除
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        itagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}
