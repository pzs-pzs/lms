package com.lms.demo.service;

import com.lms.demo.domain.BorrowBooksTable;
import com.lms.demo.repository.BorrowBookRepository;
import com.lms.demo.util.BorrowBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BorrowService {
    @Autowired
    BorrowBookRepository borrowBookRepository;

    public boolean borrow(Long bookId,Long userId) {
        BorrowBooksTable b = borrowBookRepository.save(BorrowBookUtil.rejectIn(bookId,userId));
        if (b == null) {
            return false;
        }
        return true;
    }

}
