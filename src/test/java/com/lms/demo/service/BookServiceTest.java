package com.lms.demo.service;

import com.lms.demo.domain.Book;
import com.lms.demo.query.QueryBook;
import com.lms.demo.repository.QueryBookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {
    @Autowired
    BookService service;

    @Autowired
    QueryBookRepository repository;

    @Autowired
    private RestOperations restOperations;

    @Test
    public void getBookListByType() throws Exception {
        Page<Book> page = service.getBookListByType(0,9,"Literature");
        page.forEach(System.out::println);
    }

    @Test
    public void getBookList() throws Exception {

    }

    @Test
    public void getBookById() throws Exception {
    }

    @Test
    public void getBookByKw() throws Exception {

    }

}