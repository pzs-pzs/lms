package com.lms.demo.util;

import com.lms.demo.domain.Book;
import net.sf.json.JSONObject;

public class BookForIsbnUtils {
    public static Book book(JSONObject resultarr,Book b){

        String title = resultarr.getString("title");
        b.setName(title);
        String subtitle = resultarr.getString("subtitle");

        String pic = resultarr.getString("pic");
        b.setPicture(pic);

        String author = resultarr.getString("author");
        b.setAuthorName(author);


        String summary = resultarr.getString("summary");
        b.setRemarks(summary);

        String publisher = resultarr.getString("publisher");
        String pubplace = resultarr.getString("pubplace");
        String pubdate = resultarr.getString("pubdate");
        String page = resultarr.getString("page");
        String price = resultarr.getString("price");
        String binding = resultarr.getString("binding");
        String isbn = resultarr.getString("isbn");
        String isbn10 = resultarr.getString("isbn10");
        String keyword = resultarr.getString("keyword");
        String edition = resultarr.getString("edition");
        String impression = resultarr.getString("impression");
        String language = resultarr.getString("language");
        String format = resultarr.getString("format");
        return b;
    }
}
