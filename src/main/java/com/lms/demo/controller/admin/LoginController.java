package com.lms.demo.controller.admin;

import com.lms.demo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class LoginController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/loginJudgement")
    public String loginJudge(){
        UserDetails userDetails =userService.getUserDetails();
        System.out.println(userDetails.getAuthorities().toString());
        if(userDetails.getAuthorities().toString().equals("[ROLE_ADMIN]")){
            return "redirect:admin/bookStatusList";
        }else if(userDetails.getAuthorities().toString().equals("[ROLE_NORMAL_USER]")){
            return "redirect:/web/getBookList";
        }else {
            return "";
        }
    }

}
