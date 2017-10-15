package com.lms.demo.repository;

import com.lms.demo.domain.BorrowBooksTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BorrowBookRepository extends JpaRepository<BorrowBooksTable,Long> {

    @Query("select c from BorrowBooksTable c where c.status=?1 ")
    public Page<BorrowBooksTable> findAll(Integer status, Pageable pageable);

}
