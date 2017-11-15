package com.lms.demo.domain;

import org.apache.http.entity.mime.content.InputStreamBody;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_fine_table")
public class Fine {
    @Id
    @GeneratedValue
    private Long id;

    private Long uId;
    //缴纳罚金人姓名
    private String name;

    private String isbn;

    private Long bookId;
    //缴纳罚金书名
    private String bookName;

    private String fine;

    //归还日期-创建日期
    private Date createDate = new Date();

    private Date updateDate = new Date();

    //借书日期
    private Date borrowBookDate;

    private int status;

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getBorrowBookDate() {
        return borrowBookDate;
    }

    public void setBorrowBookDate(Date borrowBookDate) {
        this.borrowBookDate = borrowBookDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
