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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    ITypeService iTypeService;


    @GetMapping("/types")
    //Pageable選domin的
    //一頁10筆; 以Id做排序 ; direction並以降冪的方式呈現
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){

        //是Page物件
        model.addAttribute("page",iTypeService.listType(pageable));


        return "admin/types";
    }

    /**
     * 返回新增"分類"頁面
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        //傳遞object到types-input.html
        model.addAttribute("type" , new Type());

        return "admin/types-input";
    }

    /**
     * 增加分類名稱
     * @Valid署名要校驗 NotBlank
     * BindingResult 接收校驗後結果使用
     * @param type
     * @param attributes
     * @return
     */
    //Type BindingResult 兩個參數是綁在一起的不然會無效
    @PostMapping("/types")
    public String post(@Valid Type type , BindingResult result , RedirectAttributes attributes){

        Type findByName = iTypeService.getTypeByname(type.getName());

        if(findByName != null){
            //arg1 name和Type.name保持一致為要校驗對象
            //arg2 驗證代碼
            //arg3 返回的消息
            result.rejectValue("name" , "nameError", "該分類名稱已經存在,不能重複");
        }

        if(result.hasErrors()){
            return "admin/types-input";
        }

        Type typeSaved = iTypeService.saveType(type);

        if (typeSaved == null){
            //操作消息顯示RedirectAttributes
            //渲染到types的message search:提示
            attributes.addFlashAttribute("message","新增失敗！");
        }
        else {
            attributes.addFlashAttribute("message","新增成功!!");
        }
        //重定向 -> 因為沒有經過樓上的types去查詢拿不到物件所以必須重定向
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id , Model model){

        model.addAttribute("type" , iTypeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 編輯更新
     * @Valid署名要校驗 NotBlank
     * BindingResult 接收校驗後結果使用
     * @param type
     * @param attributes
     * @return
     */
    //Type BindingResult 兩個參數是綁在一起的不然會無效
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type , BindingResult result ,@PathVariable Long id, RedirectAttributes attributes){

        Type findByName = iTypeService.getTypeByname(type.getName());

        if(findByName != null){
            //arg1 name和Type.name保持一致為要校驗對象
            //arg2 驗證代碼
            //arg3 返回的消息
            result.rejectValue("name" , "nameError", "該分類名稱已經存在,不能重複");
        }

        if(result.hasErrors()){
            return "admin/types-input";
        }

        Type typeSaved = iTypeService.updateType(id,type);

        if (typeSaved == null){
            //操作消息顯示RedirectAttributes
            //渲染到types的message search:提示
            attributes.addFlashAttribute("message","更新失敗！");
        }
        else {
            attributes.addFlashAttribute("message","更新成功!!");
        }
        //重定向 -> 因為沒有經過樓上的types去查詢拿不到物件所以必須重定向
        return "redirect:/admin/types";
    }

    /**
     * 刪除
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes attributes){

        iTypeService.deleteType(id);
        //渲染到types的message search:提示
        attributes.addFlashAttribute("message","刪除成功！");
        return "redirect:/admin/types";
    }











}
