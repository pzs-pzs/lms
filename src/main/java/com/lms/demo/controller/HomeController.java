package com.lms.demo.controller;

import com.lms.demo.service.HttpService;
import com.lms.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    HttpService service;
    @GetMapping("/")
    public String home() {
        return "redirect:/web/getBookList";
    }
    @GetMapping("/web/login")
    public String toLogin() {
        return "/web/login";
    }

    @RequestMapping(value = "/loginJudgement")
    public String loginJudge(){
        UserDetails userDetails =userService.getUserDetails();
        if(userDetails.getAuthorities().toString().equals("[ROLE_ADMIN]")){
            return "redirect:/admin/toBorrowBook";
        }else if(userDetails.getAuthorities().toString().equals("[ROLE_SUPER_ADMIN]")){
            return "redirect:/admin/toBorrowBook";
        }
        else if(userDetails.getAuthorities().toString().equals("[ROLE_NORMAL_USER]")){
            return "redirect:/web/getBookList";
        }else {
            return "";
        }
    }



}