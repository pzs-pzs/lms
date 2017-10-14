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
    @GetMapping("/web/login")
    public String toLogin() {
        return "web/login";
    }
    /*@RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public String adminloginjump(){
        return "web/login";
    }
    @RequestMapping(value = "/index", produces = "text/html;charset=UTF-8")
    public String loginjump(){
        return "index";
    }*/

}