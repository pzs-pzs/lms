package com.lms.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/web/getBookList";
    }
    @GetMapping("/web/login")
    public String toLogin() {
        return "/web/login";
    }

}