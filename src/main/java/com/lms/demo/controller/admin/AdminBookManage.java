package com.lms.demo.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.demo.domain.*;
import com.lms.demo.dto.BookList;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.repository.UserRepository;
import com.lms.demo.service.AdminService;
import com.lms.demo.service.BookService;
import com.lms.demo.service.BorrowService;
import com.lms.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.print.DocFlavor;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminBookManage {

    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @Value("${storage.image.books}")
    String directorypath;

    @Value("${storage.image.url.prefix}")
    String url;

    @Autowired
    UserService userService;

    @Autowired
    BorrowService borrowService;

    @RequestMapping("toAddBook")
    public String toAddBook(Model model){
        model.addAttribute("user", userService.getUser().getName());
        return "admin/admin_add_book";
    }

    @RequestMapping("toBorrowBook")
    public String toBorrowBook(Model model){
        model.addAttribute("user", userService.getUser().getName());
        return "admin/admin_borrow_book";
    }

    @RequestMapping("toAddStudent")
    public String toAddStudent(Model model){
        model.addAttribute("user", userService.getUser().getName());
        return "admin/admin_add_student";
    }

    @RequestMapping("toAddAdmin")
    public String toAddAdmin(Model model){
        model.addAttribute("user", userService.getUser().getName());
        return "admin/admin_add_lib";
    }

    @RequestMapping("toManageAdmin")
    public String toManageAdmin(Model model){
        model.addAttribute("user", userService.getUser().getName());
        List<User> adminlist = adminService.getAdminList();
        model.addAttribute("addminList",adminlist);
        return "admin/admin_info";
    }

    @RequestMapping("toManageUser")
    public String toManageUser(Model model){
        model.addAttribute("user", userService.getUser().getName());
        List<User> adminlist = adminService.getUserList();
        model.addAttribute("addminList",adminlist);
        return "admin/user_info";
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam(name = "id")Long id,Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.deleteUser(id);
        return "{\"message\":\""+message+"\"}";
    }

    @RequestMapping("startUser")
    @ResponseBody
    public String startUser(@RequestParam(name = "id")Long id,Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.startUser(id);
        return "{\"message\":\""+message+"\"}";
    }

    @RequestMapping("adminInfo")
    public String adminInfo(Model model){
        model.addAttribute("user", userService.getUser().getName());
        User admin = userService.getUser();
        model.addAttribute("admin",admin);
        return "admin/admin_edit_info";
    }

    @RequestMapping("modifySelfInfo")
    @ResponseBody
    public String ModifyAdmin(@RequestParam(name = "email") String email,
                              @RequestParam(name = "phone")String phone,
                              Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.modify(email,phone,userService.getUserId());
        return "{\"message\":\""+message+"\"}";
    }

    @RequestMapping("modifyAdmin")
    @ResponseBody
    public String ModifyAdmin(@RequestParam(name = "email") String email,
                              @RequestParam(name = "id") Long id ,
                              @RequestParam(name = "phone")String phone,
                              Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.modify(email,phone,id);
        return "{\"message\":\""+message+"\"}";
    }

    @RequestMapping("toAllPaidArrearage")
    public String PaidArrearage(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                @RequestParam(name = "size" ,required = false,defaultValue = "5") Integer s,
                                @RequestParam(name =  "userID",required = false,defaultValue = "")String userId,
                                Model model){
        model.addAttribute("user", userService.getUser().getName());
        if(userId.equals("")||userId==null){
            Page<Fine> page = adminService.getAllPaidList(p,s,1);
            model.addAttribute("fineList",page.getContent());
            model.addAttribute("page",page);
            model.addAttribute("cPage",p);
            model.addAttribute("userId",userId);
            return "admin/admin_arrearage_allpaidbyuserid";
        }else {
            Page<Fine> page = adminService.getSpecifyPaidList(p,s,1,userId);
            model.addAttribute("fineList",page.getContent());
            model.addAttribute("page",page);
            model.addAttribute("cPage",p);
            model.addAttribute("userId",userId);
            return "admin/admin_arrearage_allpaidbyuserid";
        }

    }

    @RequestMapping("toPaidArrearageByDate")
    public String PaidArrearageByDate(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                @RequestParam(name = "size" ,required = false,defaultValue = "5") Integer s,
                                @RequestParam(name =  "DateRange",required = false,defaultValue = "")String dateRange,
                                Model model){
        model.addAttribute("user", userService.getUser().getName());
        Page<Fine> page = adminService.getAllPaidListByDateRange(p,s,1,dateRange);
        model.addAttribute("fineList",page.getContent());
        model.addAttribute("page",page);
        model.addAttribute("cPage",p);
        model.addAttribute("dateRange",dateRange);
        return "admin/admin_arrearage_allpaidbydaterange";
    }


    @RequestMapping("toAllUnpaidArrearage")
    public String UnpaidArrearage(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                  @RequestParam(name = "size" ,required = false,defaultValue = "5") Integer s,
                                  @RequestParam(name =  "userID",required = false,defaultValue = "")String userId,Model model){
        model.addAttribute("user", userService.getUser().getName());
        if(userId.equals("")||userId==null){
            Page<BorrowBooksTable> page = adminService.getAllUnpaidList(p,s,1);
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
            model.addAttribute("userId",userId);
            return "admin/admin_arrearage_unpaidbyuserid";
        }else {
            Page<BorrowBooksTable> page = adminService.getSpecifyUnpaidList(p,s,1,userId);
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
            model.addAttribute("userId",userId);
            return "admin/admin_arrearage_unpaidbyuserid";
        }

    }

    @RequestMapping("/addBook")
    @ResponseBody
    public String addBook(@ModelAttribute Book book, Model model) throws Exception {
        model.addAttribute("user", userService.getUser().getName());
        book.setStatus(1);
        boolean f = bookService.addBook(book);
        Map<String,String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (f){
            map.put("status","1");
            return objectMapper.writeValueAsString(map);
        }
        map.put("status","0");
        return objectMapper.writeValueAsString(map);
    }

    @RequestMapping("/toUserInfo")
    public String toUserInfo(Model model){
        model.addAttribute("user", userService.getUser().getName());
        return "admin/admin_user_borrow_info1";
    }

    @RequestMapping("findUserInfo")
    public String finduserInfo(@RequestParam(name = "page",required = false,defaultValue = "0") Integer p,
                           @RequestParam(name = "size",required = false,defaultValue = "8") Integer s,
                           @RequestParam(name = "username",required = false,defaultValue = "")String username,
                           Model model){
        model.addAttribute("user", userService.getUser().getName());
        Map<String,Object> map = adminService.getBorrowHistory(p,s,username);
        model.addAttribute("bookList",map.get("bookList"));
        model.addAttribute("page",map.get("page"));
        model.addAttribute("cPage",p);
        model.addAttribute("username",map.get("username"));
        model.addAttribute("userID",map.get("userID"));
        return "admin/admin_user_borrow_info";
    }

    @RequestMapping("/bookStatusList")
    public String bookStatusList(@RequestParam(name = "page",required = false,defaultValue = "0") Integer p,
                                 @RequestParam(name = "size",required = false,defaultValue = "9") Integer s,
                                 @RequestParam(name = "type",required = false,defaultValue = "") String t,
                                 Model model){
        model.addAttribute("user", userService.getUser().getName());
        if(t.equals("All")||t.equals("")){
            System.out.println("aaaaaaaaaa");
            Page<BookInventory> bookStatusPage = adminService.getAllBookStatusList(p,s);
            Map<String,Object> map = adminService.getBookStatusList(p,s);
            model.addAttribute("page",bookStatusPage);
            model.addAttribute("bookStatusList",map.get("bookStatusList"));
            model.addAttribute("bookListList",map.get("bookListList"));
            model.addAttribute("cPage",p);
            model.addAttribute("type","All");
        }else {
        Page<BookInventory> bookStatusPage = adminService.getBookStatusPageByType(p,s,t);
        Map<String,Object> map = adminService.getBookStatusListBytype(bookStatusPage.getContent());
        model.addAttribute("page",bookStatusPage);
        model.addAttribute("bookStatusList",map.get("bookStatusList"));
        model.addAttribute("cPage",p);
        model.addAttribute("type",t);
        }
        return "admin/admin_book_borrow_info";
    }

    @RequestMapping("/bookInfo")
    public String specifyBookInfo(@RequestParam(name = "bookname",required = false,defaultValue = "") String bookName,
                                  Model model){
        model.addAttribute("user", userService.getUser().getName());

        return "admin/admin_book_borrow_info";
    }

    @PostMapping("addAdmin")
    @ResponseBody
    public String addAdmin(@RequestParam(name = "username",required = false)String username,
                           @RequestParam(name = "password",required = false)String password,
                           @RequestParam(name = "email",required = false)String email,
                           @RequestParam(name = "num",required = false) String num,
                           Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.AddAdmin(username,password,email,num);
        return "{\"message\":\""+message+"\"}";
    }

    @PostMapping("addStudent")
    @ResponseBody
    public String addStudent(@RequestParam(name = "username",required = false)String username,
                           @RequestParam(name = "password",required = false)String password,
                           @RequestParam(name = "email",required = false)String email,
                           @RequestParam(name = "num",required = false) String num,
                           Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.AddStudent(username,password,email,num);
        return "{\"message\":\""+message+"\"}";
    }


    @RequestMapping("BorrowBook")
    @ResponseBody
    public String BorrowBook(@RequestParam(name = "bookId",required = false)Long bookid,
                             @RequestParam(name = "userId",required = false)String userId,
                             Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.BorrowBook(bookid,userId);
        return "{\"message\":\""+message+"\"}";
    }

    @RequestMapping("ReturnBook")
    @ResponseBody
    public String ReturnBook(@RequestParam(name = "bookId",required = false)Long bookid,
                             Model model){
        model.addAttribute("user", userService.getUser().getName());
        String message = adminService.ReturnBook(bookid);
        return "{\"message\":\""+message+"\"}";
    }

    @RequestMapping("SpecifyBookList")
    @ResponseBody
    public BookList SpecifyBook(@RequestParam(name = "bookname",required = false)String bookname,
                             Model model){
        model.addAttribute("user", userService.getUser().getName());
        BookList bookList = adminService.getSpecifyBookList(bookname);
        return bookList;
    }

    @RequestMapping("/toArrearagePay")
    public String arrearagePay(@RequestParam(name = "userId",defaultValue = "",required = false)String username,
                               @RequestParam(name = "bookId",defaultValue = "",required = false)String bookId,
                               Model model){
        User user;
        if(bookId.equals("")||bookId==null){
            user = userService.getUserByUsername(username);
        }else {
            user = borrowService.getUser(new Long(bookId));
        }

        List<BorrowBooksTable> books = adminService.getNeedFineBook(user.getId());
        System.out.println(books.size());
        List<BorrowHistory> list = bookService.getBorrowHistory(books);
        Long arrearage =0L;
        for (BorrowHistory b : list) {
            Date d1 = b.getBorrowTime();
            Date d2 = new Date();
            Long fine = (d2.getTime()-d1.getTime())/86400000-15;
            arrearage+=fine;
            b.setFine(fine.toString());
        }
        model.addAttribute("arrearage",arrearage);
        model.addAttribute("fineList",list);
        model.addAttribute("user", userService.getUser().getName());
        model.addAttribute("userId",user.getId());
        model.addAttribute("username", user.getName());
        return "admin/admin_arrearage_pay";
    }

    @RequestMapping("PayMoney")
    @ResponseBody
    public String payMoney(@RequestParam(name = "userId")Long userId){
        String message = adminService.payMoney(userId);
        return "{\"message\":\""+message+"\"}";
    }

    @RequestMapping("Deletebook")
    @ResponseBody
    public String DeleteBook(@RequestParam(name = "bookId")Long bookId){
        String message = adminService.deleteBook(bookId);
        return "{\"message\":\""+message+"\"}";
    }
}
