package com.lms.demo.controller.web;

import com.lms.demo.domain.Book;
import com.lms.demo.dto.BookInfo;
import com.lms.demo.query.QueryBook;
import com.lms.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
                              @RequestParam(name = "size",required = false,defaultValue = "8") Integer s,
                              @RequestParam(name = "type",required = false,defaultValue = "") String t,
                              Model model) {
        if(t==null||t.equals("")||t.equals("All")){
            Map<String,Object> map = bookService.getBookList(p,s);
            model.addAttribute("bookList",map.get("bookList"));
            model.addAttribute("page",map.get("bookPage"));
            model.addAttribute("cPage",p);
            model.addAttribute("type","All");
            return "web/index";
        }
        Map<String,Object> map = bookService.getBookListByType(p,s,t);
        model.addAttribute("bookList",map.get("bookList"));
        model.addAttribute("page",map.get("bookPage"));
        model.addAttribute("cPage",p);
        model.addAttribute("type",t);
        return "web/index";
    }

    @GetMapping("/search")
    public String searchByKw(@RequestParam(name = "page" ,required = false,defaultValue = "0") int p,
                             @RequestParam(name = "size" ,required = false,defaultValue = "8") int s,
                             @RequestParam(name = "kw" , required = false) String kw,
                             Model model){
        if ( kw==null || StringUtils.isEmpty(kw)||kw.equals("All")) {
            Map<String,Object> map = bookService.getBookList(p,s);
            model.addAttribute("bookList",map.get("bookList"));
            model.addAttribute("page",map.get("bookPage"));
            model.addAttribute("cPage",p);
            model.addAttribute("kw",kw);
            return "web/search";
        }
        Page<QueryBook> page = bookService.getBookByKw(kw,p,s);
        model.addAttribute("bookList",page.getContent());
        model.addAttribute("page",page);
        model.addAttribute("cPage",p);
        model.addAttribute("kw",kw);
        return "web/search";
    }
}
