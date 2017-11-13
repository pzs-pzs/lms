package com.lms.demo.service;

import com.lms.demo.domain.Book;
import com.lms.demo.util.BookForIsbnUtils;
import com.lms.demo.util.HttpUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * pzs
 */
@Service
public class HttpService implements Runnable {

    @Value("${isbn.api.key}")
    private String APPKEY = "cf35a4dc8a9c5260";

    @Value("${isbn.api.url}")
    private String URL = "http://api.jisuapi.com/isbn/query";

    private String isbn = "9787115362865";

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book getFromIsbnApi(Book book) throws Exception {

        String url = URL + "?appkey=" + APPKEY + "&isbn=" + book.getIsbn();
        String result = HttpUtils.sendGet(url, "utf-8");
        System.out.println(result);
        JSONObject json = JSONObject.fromObject(result);
        if (json.getInt("status") != 0) {
            throw new Exception(json.getString("msg"));
        } else {
            JSONObject resultarr = json.optJSONObject("result");
            BookForIsbnUtils.book(resultarr,book);
        }
        return book;
    }

    @Override
    public void run() {

    }
}
