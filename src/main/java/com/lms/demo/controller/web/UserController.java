package com.lms.demo.controller.web;

import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.domain.Fine;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.service.BookService;
import com.lms.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    /**
     * 未归还的图书记录
     * @param p
     * @param s
     * @param model
     * @return
     */
    @GetMapping("/profile")
    public String toProfile(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                            @RequestParam(name = "size" ,required = false,defaultValue = "7") Integer s,
                            Model model){
        model.addAttribute("user",userService.getUser());
        Map<String,Object> map = userService.getBorrowHistory(p,s,2);
        model.addAttribute("bookList",map.get("bookList"));
        model.addAttribute("page",map.get("page"));
        model.addAttribute("cPage",p);
        return "web/profile";
    }
    /**
     * 已经归还的图书记录
     */
    @GetMapping("/borrowRecord")
    public String toBorrowRecord(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                 @RequestParam(name = "size" ,required = false,defaultValue = "7") Integer s,
                                 Model model){
        model.addAttribute("user",userService.getUser());
        Map<String,Object> map = userService.getBorrowHistory(p,s,1);
        model.addAttribute("bookList",map.get("bookList"));
        model.addAttribute("page",map.get("page"));
        model.addAttribute("cPage",p);
        return "web/borrowRecord";
    }

    /**
     * 获取缴纳罚金记录
     * @param p
     * @param s
     * @param model
     * @return
     */
    @GetMapping("/fineRecord")
    public String getFineRecord(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                @RequestParam(name = "size" ,required = false,defaultValue = "7") Integer s,
                                Model model) {
        model.addAttribute("user",userService.getUser());
        Page<Fine> page = userService.getFineHistory(p,s,1);
        model.addAttribute("fineList",page.getContent());
        model.addAttribute("page",page);
        model.addAttribute("cPage",p);
        return "web/fineRecord";
    }

    /**
     * 获取逾期书籍信息
     * @return
     */
    @GetMapping("/needFineRecord")
    public String  getNeedFineRecord(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                     @RequestParam(name = "size" ,required = false,defaultValue = "7") Integer s,
                                     Model model){
        model.addAttribute("user",userService.getUser());
        Page<BorrowBooksTable> page = userService.getNeedFineBook(p,s,1);
        List<BorrowHistory> list = bookService.getBorrowHistory(page);
        for (BorrowHistory b : list) {
            Date d1 = b.getBorrowTime();
            Date d2 = new Date();
            Long fine = (d2.getTime()-d1.getTime())/86400000-15;
            b.setFine(fine.toString());
        }
        model.addAttribute("fineList",list);
        model.addAttribute("page",page);
        model.addAttribute("cPage",p);
        return "web/needFineRecord";
    }


}
