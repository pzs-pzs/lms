package com.lms.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public String adminloginjump(){
        return "login";
    }
    @RequestMapping(value = "/index", produces = "text/html;charset=UTF-8")
    public String loginjump(){
        return "index";
    }

}