package com.lms.demo.service;

import com.lms.demo.domain.Book;
import com.lms.demo.json.BookJson;
import com.lms.demo.util.BookForIsbnUtils;
import com.lms.demo.util.HttpUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * pzs
 */
@Service
public class HttpService implements Runnable {


    @Value("${isbn.api.url}")
    private String URL = "https://api.douban.com/v2/book/isbn/";

    @Autowired
    private RestTemplate restTemplate;

    private String isbn = "9787115362865";

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book getFromIsbnApi(Book book) throws Exception {

        String url = URL + book.getIsbn();
        BookJson bookJson = restTemplate.getForObject(url, BookJson.class);
        Book b = BookForIsbnUtils.book(bookJson,book);
        return b;
    }

    @Override
    public void run() {

    }
}
