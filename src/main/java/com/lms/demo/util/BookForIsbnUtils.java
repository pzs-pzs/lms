package com.lms.demo.util;

import com.lms.demo.domain.Book;
import com.lms.demo.json.BookJson;
import net.sf.json.JSONObject;

public class BookForIsbnUtils {
    public static Book book(BookJson bj, Book b){
        StringBuilder name = new StringBuilder();
        for (String s : bj.getAuthor()){
            name.append(s);
        }
        b.setName(bj.getTitle());
        b.setAuthorName(name.toString());
        b.setRemarks(bj.getSummary());
        b.setPicture(bj.getImage());
        return b;
    }
}
