package com.lms.demo.util;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BookInventory;
import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.domain.User;
import com.lms.demo.dto.BookStatus;
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

    public static BorrowHistory getBorrowHistory(BorrowBooksTable t, Book b,User user){
        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setAutherName(b.getAutherName());
        borrowHistory.setBorrowStatus(t.getStatus());
        borrowHistory.setBorrowTime(t.getCreateDate());
        borrowHistory.setIsbn(b.getIsbn());
        borrowHistory.setName(b.getName());
        borrowHistory.setUserName(user.getName());
        return borrowHistory;
    }

    public static BookStatus getBookStatusList(BookInventory bookInventory, Book b){
        BookStatus bookStatus = new BookStatus();
        bookStatus.setAuthor(b.getAutherName());
        bookStatus.setBookName(b.getName());
        bookStatus.setIsbn(b.getIsbn());
        bookStatus.setPress(b.getPress());
        bookStatus.setBorrowQuantity(bookInventory.getBookBorrowQuantity());
        bookStatus.setTotalQuantity(bookInventory.getBookTotalQuantity());
        return bookStatus;
    }
}
