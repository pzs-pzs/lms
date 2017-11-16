package com.lms.demo.controller.admin;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BookInventory;
import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.domain.Fine;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.service.AdminService;
import com.lms.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
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

    @RequestMapping("toAddBook")
    public String toAddBook(){
        return "admin/admin_add_book";
    }

    @RequestMapping("toBorrowBook")
    public String toBorrowBook(){
        return "admin/admin_borrow_book";
    }

    @RequestMapping("toAddAdmin")
    public String toAddAdmin(){
        return "admin/admin_add_lib";
    }

    @RequestMapping("toAllPaidArrearage")
    public String PaidArrearage(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                @RequestParam(name = "size" ,required = false,defaultValue = "8") Integer s,
                                Model model){
        Page<Fine> page = adminService.getAllPaidList(p,s,1);
        model.addAttribute("fineList",page.getContent());
        model.addAttribute("page",page);
        model.addAttribute("cPage",p);
        return "admin/admin_arrearage_allpaid";
    }

    @RequestMapping("toAllUnpaidArrearage")
    public String UnpaidArrearage(@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer p,
                                  @RequestParam(name = "size" ,required = false,defaultValue = "8") Integer s,
                                  Model model){
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
        return "admin/admin_arrearage_unpaid";
    }

    @RequestMapping("/addBook")
    @ResponseBody
    public String addBook(@ModelAttribute Book book,
                          @RequestParam("imgs") List<String> imgs,
                          @RequestParam("quantity") String quantity,
                          @RequestParam("booktype" )String[] booktype,
                          Model model) throws IOException {
        Iterator iterator = imgs.iterator();
        while(iterator.hasNext()){
            if (iterator.next().equals("data:image/jpeg;base64"))
                iterator.remove();
        }
        String path="";
        for(String imgUrl:imgs){
            byte dataByte[] = Base64.decode(imgUrl.getBytes());
            String fileName=System.currentTimeMillis()+""+".jpg";
            OutputStream out=new FileOutputStream(directorypath+booktype[0]+"/"+fileName);
            path = url + "/books"+"/"+booktype[0]+"/" + fileName;
            out.write(dataByte);
            out.flush();
            out.close();

        }
            bookService.save(book,booktype,path,Integer.parseInt(quantity));
        return "{\"status\":\"1\"}";
    }

    @RequestMapping("/toUserInfo")
    public String toUserInfo(){
        return "admin/admin_user_borrow_info1";
    }

    @RequestMapping("findUserInfo")
    public String finduserInfo(@RequestParam(name = "page",required = false,defaultValue = "0") Integer p,
                           @RequestParam(name = "size",required = false,defaultValue = "8") Integer s,
                           @RequestParam(name = "username",required = false,defaultValue = "")String username,
                           Model model){
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

        if(t.equals("")||t==null){
            Page<BookInventory> bookStatusPage = adminService.getAllBookStatusList(p,s);
            Map<String,Object> map = adminService.getBookStatusList(p,s);
            model.addAttribute("page",bookStatusPage);
            model.addAttribute("bookStatusList",map.get("bookStatusList"));
            model.addAttribute("cPage",p);
            model.addAttribute("type","All");
            System.out.println("?????????????1");
        }else {
        Page<BookInventory> bookStatusPage = adminService.getBookStatusPageByType(p,s,t);
        Map<String,Object> map = adminService.getBookStatusListBytype(bookStatusPage.getContent());
        model.addAttribute("page",bookStatusPage);
        model.addAttribute("bookStatusList",map.get("bookStatusList"));
        model.addAttribute("cPage",p);
        model.addAttribute("type",t);
            System.out.println("?????????????2");
        }
        return "admin/admin_book_borrow_info";
    }

    @PostMapping("addAdmin")
    @ResponseBody
    public String addAdmin(@RequestParam(name = "username",required = false)String username,
                           @RequestParam(name = "password",required = false)String password,
                           @RequestParam(name = "email",required = false)String email,
                           @RequestParam(name = "num",required = false) String num){
        String message = adminService.AddAdmin(username,password,email,num);
        return "{\"message\":\""+message+"\"}";
    }


    @RequestMapping("/pay")
    public String pay(){
        return "admin/pay";
    }
}
