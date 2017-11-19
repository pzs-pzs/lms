package com.lms.demo.dto;

import com.lms.demo.domain.Book;

import java.util.List;

public class BookList {
    List<Book> specifyBookList;
    List<String> borrower;

    public List<String> getBorrower() {
        return borrower;
    }

    public void setBorrower(List<String> borrower) {
        this.borrower = borrower;
    }

    public List<Book> getSpecifyBookList() {
        return specifyBookList;
    }

    public void setSpecifyBookList(List<Book> specifyBookList) {
        this.specifyBookList = specifyBookList;
    }
}
