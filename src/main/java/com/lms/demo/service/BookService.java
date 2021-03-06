package com.lms.demo.service;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BookInventory;
import com.lms.demo.repository.BookInventoryRepository;
import com.lms.demo.repository.BookRepository;
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
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookInventoryRepository bookInventoryRepository;
    /**
     * 通过标签进行分类查询
     * @param type 类型
     * @return list
     */
    public Page<Book> getBookListByType(Integer page,Integer size,String type) {
        Specification<Book> specification = new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> t = root.get("type");
                Path<Integer> s = root.get("status");
                Predicate p1 = criteriaBuilder.like(t,"%"+type+"%");
                Predicate p2 = criteriaBuilder.equal(s,"2");
                return criteriaBuilder.and(p1,p2);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        return bookRepository.findAll(specification,pageable);
    }

    /**
     * 分页返回书籍信息
     * @param page
     * @param size
     * @return
     */
    public Page<Book> getBookList(int page,int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        Page<Book> pageList = bookRepository.findAll(2,pageable);
        return pageList;
    }

    /**
     * 查看书籍详细信息
     * @param id
     * @return
     */
    public Book getBookById(Long id) {
        return bookRepository.findOne(id);
    }

    /**
     * 关键字查询书籍
     * @param kw
     * @return
     */
    public List<Book> getBookByKw(String kw) {
        return new ArrayList<>();
    }

    /**
     *添加图书
     */

    public void save(Book entity,String[] booktype,String path,Integer quantity){
        String allType = "";
        for(String type:booktype){
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            allType += type+",";
        }
        entity.setType(allType);
        entity.setPicture(path);
        entity.setStatus(2);
        bookRepository.save(entity);
        if(bookInventoryRepository.findOneByBookName(entity.getName())!=null){
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

}
