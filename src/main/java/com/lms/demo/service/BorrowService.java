package com.lms.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BorrowService {
    public boolean borrow(Long bookId,Long userId) {

        return true;
    }

}
