package com.lms.demo.service;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BookInventory;
import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.domain.User;
import com.lms.demo.dto.BookInfo;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.query.QueryBook;
import com.lms.demo.repository.BookInventoryRepository;
import com.lms.demo.repository.BookRepository;
import com.lms.demo.repository.QueryBookRepository;
import com.lms.demo.repository.UserRepository;
import com.lms.demo.util.BarCodeUtils;
import com.lms.demo.util.BorrowBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    QueryBookRepository queryBookRepository;

    @Autowired
    BookInventoryRepository bookInventoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpService httpService;

    /**
     * 通过标签进行分类查询
     * @param type 类型
     * @return list
     */
    public Map<String,Object> getBookListByType(Integer page, Integer size, String type) {
        Specification<BookInventory> specification = new Specification<BookInventory>() {
            @Override
            public Predicate toPredicate(Root<BookInventory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> t = root.get("bookType");
                Path<Integer> s = root.get("status");
                Predicate p1 = criteriaBuilder.like(t, "%" + type + "%");
                Predicate p2 = criteriaBuilder.equal(s, "1");
                return criteriaBuilder.and(p1, p2);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<BookInventory> p = bookInventoryRepository.findAll(specification, pageable);
        List<BookInfo> list = new ArrayList<>();
        for (BookInventory bookInventory : p.getContent()) {
            Book book = bookRepository.findTopOrOrderByName(bookInventory.getBookName());
            BookInfo bookInfo = BorrowBookUtil.getBookInfo(book,bookInventory);
            list.add(bookInfo);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("bookPage",p);
        map.put("bookList",list);
        return map;


    }

    /**
     * 分页返回书籍信息
     * @param page
     * @param size
     * @return
     */
    public Map<String,Object> getBookList(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        Page<BookInventory> pageList = bookInventoryRepository.findAll(1,pageable);
        List<BookInfo> list = new ArrayList<>();
        for (BookInventory bookInventory : pageList.getContent()) {
            Book book = bookRepository.findTopOrOrderByName(bookInventory.getBookName());
            BookInfo bookInfo = BorrowBookUtil.getBookInfo(book,bookInventory);
            list.add(bookInfo);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("bookPage",pageList);
        map.put("bookList",list);
        return map;
    }

    /**
     * 查看书籍详细信息
     * @param id
     * @return
     */
    public BookInfo getBookById(Long id) {
        BookInventory bookInventory =  bookInventoryRepository.findOne(id);
        Book book = bookRepository.findTopOrOrderByName(bookInventory.getBookName());
        return BorrowBookUtil.getBookInfo(book,bookInventory);
    }

    /**
     * 关键字查询书籍
     * @param kw
     * @return
     */
    public Page<QueryBook> getBookByKw(String kw, int p, int s) {

        return queryBookRepository.findByNameContaining(kw,new PageRequest(p,s) );

    }

    /**
     *添加图书
     */

    public void save(Book entity, String[] booktype, String path, Integer quantity){
        String allType = "";
        for(String type:booktype){
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            allType += type+",";
        }
        entity.setType(allType);
        entity.setPicture(path);
        entity.setStatus(2);
        bookRepository.save(entity);
        if(bookInventoryRepository.findByBookName(entity.getName(),1)!=null){
            bookInventoryRepository.addBook(entity.getName());
        }else {
            BookInventory bookInventory = new BookInventory();
            bookInventory.setBookName(entity.getName());
            bookInventory.setBookType(entity.getType());
            bookInventory.setBookBorrowQuantity(0);
            bookInventory.setBookTotalQuantity(quantity);
            bookInventoryRepository.save(bookInventory);
        }

    }
    @Value("${storage.image.barcode}")
    String path;
    /**
     * 添加图书
     * @param b
     */
    public boolean addBook(Book b) throws Exception {
        Book book = httpService.getFromIsbnApi(b);
        Book reBook = bookRepository.save(book);
        BarCodeUtils.generateFile(reBook.getId()+"", path+reBook.getId()+".png");
        boolean f = false;
        if(bookInventoryRepository.findByBookName(book.getName(),1)!=null){
            if(bookInventoryRepository.addBook(book.getName())==1){
                f = true;
           }
        }else {
            BookInventory bookInventory = new BookInventory();
            bookInventory.setBookName(book.getName());
            bookInventory.setBookType(book.getType());
            bookInventory.setBookBorrowQuantity(0);
            bookInventory.setBookTotalQuantity(1);
            bookInventory.setStatus(1);
            book.setStatus(1);
            if(bookInventoryRepository.save(bookInventory)!=null){
                f = true;
            }
        }
        if (reBook==null||!f){
            return false;
        }
        return true;

    }

    public List<BorrowHistory> getBorrowHistory(Page<BorrowBooksTable> p) {
        List<BorrowHistory> bookList = new ArrayList<>();
        List<BorrowBooksTable> borrowBooksTables = p.getContent();
        for (BorrowBooksTable b : borrowBooksTables) {
            Book book = bookRepository.findOne(b.getBookId());
            User user = userRepository.getOne(b.getUserId());
            BorrowHistory borrowHistory = BorrowBookUtil.getBorrowHistory(b,book,user);
            bookList.add(borrowHistory);
        }
        return bookList;

    }

    public List<BorrowHistory> getBorrowHistory(List<BorrowBooksTable> list) {
        List<BorrowHistory> bookList = new ArrayList<>();
        List<BorrowBooksTable> borrowBooksTables = list;
        for (BorrowBooksTable b : borrowBooksTables) {
            Book book = bookRepository.findOne(b.getBookId());
            User user = userRepository.getOne(b.getUserId());
            BorrowHistory borrowHistory = BorrowBookUtil.getBorrowHistory(b,book,user);
            bookList.add(borrowHistory);
        }
        return bookList;

    }

    public List<Book> getSpecifyBookList(String name){
        return  bookRepository.findAllByName(name);
    }

}
