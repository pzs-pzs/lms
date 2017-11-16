package com.lms.demo.service;

import com.lms.demo.domain.*;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.repository.*;
import com.lms.demo.util.BorrowBookUtil;
import com.lms.demo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BorrowBookRepository borrowBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FineRepository fineRepository;

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

    /**
     * 借书表根据status判断书是否归还 1-未还 2-归还
     * @param page
     * @param size
     * @param status
     * @return
     */
    public Map<String,Object> getBorrowHistory(Integer page, Integer size,int status) {
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        Page<BorrowBooksTable> p = borrowBookRepository.findAllByUserId(status,getUserId(),pageRequest);
        List<BorrowHistory> bookList = new ArrayList<>();
        List<BorrowBooksTable> borrowBooksTables = p.getContent();
        for (BorrowBooksTable b : borrowBooksTables) {
            Book book = bookRepository.findOne(1,b.getBookId());
            User user = userRepository.findOne(b.getUserId());
            BorrowHistory borrowHistory = BorrowBookUtil.getBorrowHistory(b,book,user);
            bookList.add(borrowHistory);
        }
        map.put("page",p);
        map.put("bookList",bookList);
        return map;
    }

    /**
     * 获取缴纳罚金记录
     * @param page
     * @param size
     * @param status
     * @return
     */
    public Page<Fine> getFineHistory(int page,int size,int status) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        return fineRepository.findAllByUserId(status,getUserId(),pageRequest);
    }

    /**
     * 未缴罚金书籍
     * @param page
     * @param size
     * @param status
     * @return
     */
    public Page<BorrowBooksTable> getNeedFineBook(int page,int size,int status) {
        Long uid = getUserId();
        Date date = DateUtils.getBeforeDays(new Date(),15);
        Specification<BorrowBooksTable> specification = new Specification<BorrowBooksTable>() {
            @Override
            public Predicate toPredicate(Root<BorrowBooksTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Date> createDate = root.get("createDate");
                Path<Integer> status = root.get("status");
                Path<Long> userId = root.get("userId");
                Predicate p1 = criteriaBuilder.equal(status,"1");
                Predicate p2 = criteriaBuilder.equal(userId,uid);
                Predicate p3 = criteriaBuilder.lessThan(createDate.as(String.class),date.toString());
                return criteriaBuilder.and(p1,p2,p3);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        return borrowBookRepository.findAll(specification,pageable);
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
