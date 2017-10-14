package com.lms.demo.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.demo.service.BorrowService;
import com.lms.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class BookBorrowController {
    @Autowired
    BorrowService borrowService;

    @Autowired
    UserService userService;
    @GetMapping("/borrowBook")
    @ResponseBody
    public String borrow(@RequestParam(name = "id",required = false) Long bookId) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (!userService.isLogin()){
            map.put("error",2);
            return objectMapper.writeValueAsString(map);
        }
        boolean f = borrowService.borrow(bookId,1L);

        if (f){
            map.put("error",0);
            return objectMapper.writeValueAsString(map);
        }
        map.put("error",1);
        return objectMapper.writeValueAsString(map);
    }

}
