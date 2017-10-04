package com.lms.demo.controller;

import com.lms.demo.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public String systemExceptionHandler(Model model, HttpServletRequest req, ServiceException e) {
        model.addAttribute("code", "-1");
        model.addAttribute("error", "发生系统异常，无法继续进行！");
        model.addAttribute("description", e.getMessage());
        return "error";
    }

}
