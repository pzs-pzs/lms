package com.lms.demo.controller.web;

import com.lms.demo.domain.Book;
import com.lms.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/web")
public class BookListController {
    @Autowired
    BookService bookService;
     /*
      获取书籍列表
     */
    @GetMapping("/getBookList")
    public String getBookList(@RequestParam(name = "page",required = false,defaultValue = "0") Integer p,
                              @RequestParam(name = "size",required = false,defaultValue = "9") Integer s,
                              @RequestParam(name = "type",required = false,defaultValue = "") String t,
                              Model model) {
        if(t==null||t.equals("")){
            Page<Book> bookPage = bookService.getBookList(p,s);
            model.addAttribute("bookList",bookPage.getContent());
            model.addAttribute("page",bookPage);
            model.addAttribute("cPage",p);
            model.addAttribute("type",t);
            return "web/index";
        }
        Page<Book> bookPage = bookService.getBookListByType(p,s,t);
        model.addAttribute("bookList",bookPage.getContent());
        model.addAttribute("page",bookPage);
        model.addAttribute("cPage",p);
        model.addAttribute("type",t);
        return "web/index";
    }
    /*
        获取书籍的详细信息
     */
    @GetMapping("/getBookDetail")
    public String getBookDetail(@RequestParam(name = "id") Long id) {
        Book book = bookService.getBookById(id);
        return "web/demo";
    }
}
