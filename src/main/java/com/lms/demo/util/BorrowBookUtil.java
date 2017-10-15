package com.lms.demo.util;

import com.lms.demo.domain.BorrowBooksTable;

import java.util.Date;

public class BorrowBookUtil {
    public static BorrowBooksTable rejectIn(Long bookId,Long userId){
        BorrowBooksTable borrowBooksTable = new BorrowBooksTable();
        borrowBooksTable.setBookId(bookId);
        borrowBooksTable.setStatus(1);
        borrowBooksTable.setUserId(userId);
        borrowBooksTable.setCreateDate(new Date());
        return borrowBooksTable;
    }
}
