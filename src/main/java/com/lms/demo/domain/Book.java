package com.lms.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "t_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    //国际标准书号
    private String isbn;
    private String authorName;



    //图书借阅状态0-未归还 1-归还 2-禁止借阅
    private Integer bStatus;


    //出版社
    private String press;
    //备注
    private String remarks;
    //出版时间
    private String publishTime;
    //数据库是否删除 1有效
    private Integer status;
    //图片链接
    private String picture;
    //书籍类型，多种类型用逗号隔开
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateDate = new Date();
    @Column(name="location")
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutherName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

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

    public Integer getbStatus() {
        return bStatus;
    }

    public void setbStatus(Integer bStatus) {
        this.bStatus = bStatus;
    }
}
