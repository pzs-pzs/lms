package com.lms.demo.service;

import com.lms.demo.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpServiceTest {
    @Autowired
    HttpService service;
    @Test
    public void getFromIsbnApi() throws Exception {

        Book b = new Book();
        b.setIsbn("9787115362865");
        System.out.println(service.getFromIsbnApi(b).getName());

    }

}