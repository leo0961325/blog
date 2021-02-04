package com.blog.controller.admin;

import com.blog.model.User;
import com.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    IUserService iUserService;


    @GetMapping()
    public String loginPage(){

        //這個是html
        return "admin/login";
    }

    //有使用HttpSession
    //這裡是域名
    @PostMapping("/login")
    public String login(@RequestParam String username ,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){

        User user = iUserService.checkUser(username,password);

        if (user != null){
            session.setAttribute("user" , user);

            user.setPassword(null);
            return "admin/indexAdmin";

        }
        else {
            //RedirectAttributes查看用戶名及密碼
            attributes.addFlashAttribute("message","用戶名及密碼錯誤");
            //重新定向，返回一開始mapping page ->loginPage
            return "redirect:/admin";
        }

    }


    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("user");
        return "redirect:/admin";
    }




}
