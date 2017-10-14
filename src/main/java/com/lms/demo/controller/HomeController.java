package com.lms.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/web/getBookList";
    }
    @GetMapping("/login")
    public String toLogin() {
        return "/web/login";
    }
    @GetMapping("/toRegister")
    public String toRegister() {
        return "/web/register";
    }


}