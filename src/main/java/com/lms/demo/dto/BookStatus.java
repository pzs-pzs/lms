package com.lms.demo.dto;

public class BookStatus {
    private Long bookid;
    private String bookName;
    private String isbn;
    private String author;
    private String press;
    private Integer totalQuantity;
    private Integer borrowQuantity;

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getBorrowQuantity() {
        return borrowQuantity;
    }

    public void setBorrowQuantity(Integer borrowQuantity) {
        this.borrowQuantity = borrowQuantity;
    }
}
