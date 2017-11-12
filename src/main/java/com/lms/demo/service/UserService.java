package com.lms.demo.service;

import com.lms.demo.domain.*;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.repository.BookRepository;
import com.lms.demo.repository.BorrowBookRepository;
import com.lms.demo.repository.UserRepository;
import com.lms.demo.repository.UserRoleRepository;
import com.lms.demo.util.BorrowBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    BorrowBookRepository borrowBookRepository;

    @Autowired
    BookRepository bookRepository;

    public Long getUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String uname = userDetails.getUsername();
        long id = userRepository.findByName(uname).getId();
        return id;
    }

    /**
     * 判断是否登录
     * @return
     */
    public boolean isLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return true;
        }
        return false;

    }

    public boolean createUser(String username, String password,
                              String email){
        User user = new User();
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(4);
        user.setPassword(encoder.encode(password));
        user.setName(username);
        user.setEnabled(true);
        user.setEmail(email);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        User result = userRepository.save(user);
        long rid = new Long(2);
        UserRole userRole = new UserRole(userRepository.findByName(username).getId(),rid);
        userRoleRepository.save(userRole);
        if(result!=null){
            return true;
        }else{
            return false;
        }
    }

    public User getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String uname = userDetails.getUsername();
        return userRepository.findByName(uname);
    }

    public Map<String,Object> getBorrowHistory(Integer page, Integer size) {
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        Page<BorrowBooksTable> p = borrowBookRepository.findAllByUserId(1,getUserId(),pageRequest);
        List<BorrowHistory> bookList = new ArrayList<>();
        List<BorrowBooksTable> borrowBooksTables = p.getContent();
        for (BorrowBooksTable b : borrowBooksTables) {
            Book book = bookRepository.findOne(2,b.getBookId());
            User user = userRepository.findOne(b.getUserId());
            BorrowHistory borrowHistory = BorrowBookUtil.getBorrowHistory(b,book,user);
            bookList.add(borrowHistory);
        }
        map.put("page",p);
        map.put("bookList",bookList);
        return map;
    }

    public UserDetails getUserDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails;
    }

    public boolean createAdmin(String username, String password,
                              String email){
        User user = new User();
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(4);
        user.setPassword(encoder.encode(password));
        user.setName(username);
        user.setEnabled(true);
        user.setEmail(email);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        User result = userRepository.save(user);
        long rid = new Long(1);
        UserRole userRole = new UserRole(userRepository.findByName(username).getId(),rid);
        userRoleRepository.save(userRole);
        if(result!=null){
            return true;
        }else{
            return false;
        }
    }

}
