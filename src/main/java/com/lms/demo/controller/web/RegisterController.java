package com.lms.demo.controller.web;

import com.lms.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String addAdmin(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "email") String email, Model model){
        System.out.println("enter register");
        boolean result = userService.createUser(username,password,email);
        if(result)
            return "/web/login";
        else
            return "/toRegister";
    }
}
