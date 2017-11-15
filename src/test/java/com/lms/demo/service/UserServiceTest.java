package com.lms.demo.service;

import com.lms.demo.domain.BorrowBooksTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Test
    public void getNeedFineBook() throws Exception {
        Page<BorrowBooksTable> p = userService.getNeedFineBook(0,10,1);
        for (BorrowBooksTable b : p.getContent()) {
            System.out.println(b.getBookId());
        }
    }

}