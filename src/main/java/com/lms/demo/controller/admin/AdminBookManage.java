package com.lms.demo.controller.admin;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BookInventory;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.repository.BookInventoryRepository;
import com.lms.demo.repository.BookRepository;
import com.lms.demo.service.AdminService;
import com.lms.demo.service.BookService;
import com.lms.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
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
        return "admin/admin_submit";
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

    @RequestMapping("toUserInfo")
    public String toUserInfo(){
        return "admin/admin_list";
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam(name = "page",required = false,defaultValue = "0") Integer p,
                           @RequestParam(name = "size",required = false,defaultValue = "9") Integer s,
                           Model model){
        Map<String,Object> map = adminService.getBorrowHistory(p,s);
        model.addAttribute("bookList",map.get("bookList"));
        model.addAttribute("page",map.get("page"));
        model.addAttribute("cPage",p);
        return "admin/admin_list";
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
            model.addAttribute("type",t);
        }else {
        Page<BookInventory> bookStatusPage = adminService.getBookStatusPageByType(p,s,t);
        Map<String,Object> map = adminService.getBookStatusListBytype(bookStatusPage.getContent());
        model.addAttribute("page",bookStatusPage);
        model.addAttribute("bookStatusList",map.get("bookStatusList"));
        model.addAttribute("cPage",p);
        model.addAttribute("type",t);
        }
        return "admin/admin_booklist";
    }
}
