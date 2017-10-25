package com.lms.demo.util;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.dto.BorrowHistory;

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

    public static BorrowHistory getBorrowHistory(BorrowBooksTable t, Book b){
        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setAutherName(b.getAutherName());
        borrowHistory.setBorrowStatus(t.getStatus());
        borrowHistory.setBorrowTime(t.getCreateDate());
        borrowHistory.setIsbn(b.getIsbn());
        borrowHistory.setName(b.getName());
        return borrowHistory;
    }
}
