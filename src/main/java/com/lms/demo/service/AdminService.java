package com.lms.demo.service;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BookInventory;
import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.domain.User;
import com.lms.demo.dto.BookStatus;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.repository.BookInventoryRepository;
import com.lms.demo.repository.BookRepository;
import com.lms.demo.repository.BorrowBookRepository;
import com.lms.demo.repository.UserRepository;
import com.lms.demo.util.BorrowBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String,Object> getBorrowHistory(Integer page, Integer size) {
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        Page<BorrowBooksTable> p = borrowBookRepository.findAll(pageRequest);
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

    public Map<String,Object> getBookStatusList(Integer page,Integer size){
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        Pageable pageable = new PageRequest(page,size,sort);
        List<BookInventory> bookInventoryList = bookInventoryRepository.findAll(pageable).getContent();
        List<BookStatus> bookStatusList = new ArrayList<>();
        for (BookInventory b : bookInventoryList) {
            Book book = bookRepository.findOneByName(b.getBookName());
            BookStatus bookStatus = BorrowBookUtil.getBookStatusList(b,book);
            bookStatusList.add(bookStatus);
        }
        map.put("page",page);
        map.put("bookStatusList",bookStatusList);
        return map;
    }

    public Page<BookInventory> getAllBookStatusList(int page,int size){
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        Page<BookInventory> bookInventoryPage = bookInventoryRepository.findAll(pageable);
        return bookInventoryPage;
    }

    public Page<BookInventory> getBookStatusPageByType(Integer page,Integer size,String type) {
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
        return bookInventoryRepository.findAll(specification,pageable);
    }

    public Map<String,Object> getBookStatusListBytype(List<BookInventory> bookInventoryList){
        Map<String,Object> map = new HashMap<>();
        List<BookStatus> bookStatusList = new ArrayList<>();
        for (BookInventory b : bookInventoryList) {
            Book book = bookRepository.findOneByName(b.getBookName());
            BookStatus bookStatus = BorrowBookUtil.getBookStatusList(b,book);
            bookStatusList.add(bookStatus);
        }
        map.put("bookStatusList",bookStatusList);
        return map;
    }
}
