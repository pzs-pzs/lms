package com.lms.demo.service;

import com.lms.demo.domain.*;
import com.lms.demo.dto.BookStatus;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
@Transactional
public class AdminService {

    @Autowired
    BorrowBookRepository borrowBookRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookInventoryRepository bookInventoryRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    FineRepository fineRepository;

    public Map<String,Object> getBorrowHistory(Integer page, Integer size,String username) {
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        User user = userRepository.findByName(username);
        if(user==null){
            System.out.println(page);
            map.put("page",page);
            return map;
        }
        Page<BorrowBooksTable> p = borrowBookRepository.findAllByUser(user.getId(),pageRequest);
        List<BorrowHistory> bookList = new ArrayList<>();
        List<BorrowBooksTable> borrowBooksTables = p.getContent();
        for (BorrowBooksTable b : borrowBooksTables) {
            Book book = bookRepository.findOne(1,b.getBookId());
            BorrowHistory borrowHistory = BorrowBookUtil.getBorrowHistory(b,book,user);
            bookList.add(borrowHistory);
        }
        map.put("page",p);
        map.put("bookList",bookList);
        map.put("username",user.getName());
        map.put("userID",user.getId());
        return map;
    }

    public Map<String,Object> getBookStatusList(Integer page,Integer size){
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        Pageable pageable = new PageRequest(page,size,sort);
        List<BookInventory> bookInventoryList = bookInventoryRepository.findAll(pageable).getContent();
        List<BookStatus> bookStatusList = new ArrayList<>();
        for (BookInventory b : bookInventoryList) {
            Book book = bookRepository.findTopOrOrderByName(b.getBookName());
            BookStatus bookStatus = BorrowBookUtil.getBookStatusList(b,book);
            bookStatusList.add(bookStatus);
        }
        map.put("page",page);
        map.put("bookStatusList",bookStatusList);
        return map;
    }

    public Page<BookInventory> getAllBookStatusList(int page, int size){
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        Page<BookInventory> bookInventoryPage = bookInventoryRepository.findAll(pageable);
        return bookInventoryPage;
    }

    public Page<BookInventory> getBookStatusPageByType(Integer page, Integer size, String type) {
        Specification<BookInventory> specification = new Specification<BookInventory>() {
            @Override
            public Predicate toPredicate(Root<BookInventory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> t = root.get("bookType");
                Predicate p1 = criteriaBuilder.like(t,"%"+type+"%");
                return criteriaBuilder.and(p1);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        List<BookInventory>  bookInventoryList = bookInventoryRepository.findAll(specification,pageable).getContent();
        System.out.println(bookInventoryList.size());
        return bookInventoryRepository.findAll(specification,pageable);
    }

    public Map<String,Object> getBookStatusListBytype(List<BookInventory> bookInventoryList){
        Map<String,Object> map = new HashMap<>();
        List<BookStatus> bookStatusList = new ArrayList<>();
        for (BookInventory b : bookInventoryList) {
            Book book = bookRepository.findTopOrOrderByName(b.getBookName());
            BookStatus bookStatus = BorrowBookUtil.getBookStatusList(b,book);
            bookStatusList.add(bookStatus);
        }
        map.put("bookStatusList",bookStatusList);
        return map;
    }

    public String AddAdmin(String username, String password,
                               String email,String num){
        User users = userRepository.findByName(username);
        if(users!=null){
            return "Username is not available";
        }
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
            return "Add administrator sueecss";
        }else{
            return "Add administrator failed";
        }
    }

    public Page<Fine> getAllPaidList(int page, int size, int status){
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        return fineRepository.findAllByStatus(status,pageRequest);
    }

    public Page<BorrowBooksTable> getAllUnpaidList(int page, int size, int status) {
        Date date = DateUtils.getBeforeDays(new Date(),15);
        Specification<BorrowBooksTable> specification = new Specification<BorrowBooksTable>() {
            @Override
            public Predicate toPredicate(Root<BorrowBooksTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Date> createDate = root.get("createDate");
                Path<Integer> status = root.get("status");
                Path<Long> userId = root.get("userId");
                Predicate p1 = criteriaBuilder.equal(status,"1");
                Predicate p3 = criteriaBuilder.lessThan(createDate.as(String.class),date.toString());
                return criteriaBuilder.and(p1,p3);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        return borrowBookRepository.findAll(specification,pageable);
    }
}
