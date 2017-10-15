package com.lms.demo.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/profile")
    public String toProfile(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                            @RequestParam(name = "size" ,required = false,defaultValue = "9") Integer s,
                            Model model){
        model.addAttribute("user",userService.getUser());
        Map<String,Object> map = userService.getBorrowHistory(p,s);
        model.addAttribute("bookList",map.get("bookList"));
        model.addAttribute("page",map.get("page"));
        model.addAttribute("cPage",p);
        return "web/profile";
    }








    @GetMapping("/isLogin")
    @ResponseBody
    public String isLogin() throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (!userService.isLogin()){
            map.put("error",2);
            return objectMapper.writeValueAsString(map);
        }
        map.put("error",0);
        return objectMapper.writeValueAsString(map);

    }

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
