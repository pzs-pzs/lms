package com.lms.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_book_inventory")
public class BookInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "book_type")
    private String bookType;
    @Column(name = "book_total_quantity")
    private int bookTotalQuantity;
    @Column(name = "book_borrow_quantity")
    private int bookBorrowQuantity;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateDate;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getBookTotalQuantity() {
        return bookTotalQuantity;
    }

    public void setBookTotalQuantity(int bookTotalQuantity) {
        this.bookTotalQuantity = bookTotalQuantity;
    }

    public int getBookBorrowQuantity() {
        return bookBorrowQuantity;
    }

    public void setBookBorrowQuantity(int bookBorrowQuantity) {
        this.bookBorrowQuantity = bookBorrowQuantity;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookName() {
        return bookName;

    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
