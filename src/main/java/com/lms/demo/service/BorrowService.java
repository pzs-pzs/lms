package com.lms.demo.service;

import com.lms.demo.domain.Book;
import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.domain.User;
import com.lms.demo.repository.BookInventoryRepository;
import com.lms.demo.repository.BookRepository;
import com.lms.demo.repository.BorrowBookRepository;
import com.lms.demo.repository.UserRepository;
import com.lms.demo.util.BorrowBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BorrowService {
    @Autowired
    BorrowBookRepository borrowBookRepository;

    @Autowired
    BookInventoryRepository bookInventoryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    public boolean borrow(Long bookId,Long userId) {
        /**
         * release 1
         */
//        BorrowBooksTable b = borrowBookRepository.save(BorrowBookUtil.rejectIn(bookId,userId));
//        if (b == null) {
//            return false;
//        }
//        return true;
        /**
         * release 2
         */
        Book book = bookRepository.findOne(bookId);

        if(bookInventoryRepository.findByBookName(book.getName(),1).getBookTotalQuantity()-bookInventoryRepository.findByBookName(book.getName(),1).getBookBorrowQuantity()>0){
            BorrowBooksTable b = borrowBookRepository.save(BorrowBookUtil.rejectIn(bookId,userId));
            if (b == null) {
                return false;
            }
            bookInventoryRepository.borrowBook(book.getName());
            return true;
        }else{
            return false;
        }

    }


    public User getUser(Long bookId){
        BorrowBooksTable borrowBooksTable =borrowBookRepository.findoneByBookIdAndStatus(bookId,1);
        User user = userRepository.getOne(borrowBooksTable.getUserId());
        return user;
    }
}
