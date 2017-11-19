package com.lms.demo.repository;

import com.lms.demo.domain.BorrowBooksTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowBookRepository extends JpaRepository<BorrowBooksTable,Long>,JpaSpecificationExecutor<BorrowBooksTable> {

    @Query("select c from BorrowBooksTable c where c.status=?1 and c.userId=?2 ")
    public Page<BorrowBooksTable> findAllByUserId(Integer status, Long id, Pageable pageable);

    public Page<BorrowBooksTable> findAll(Pageable pageable);

    @Query("select c from BorrowBooksTable c where c.userId=?1 ")
    public Page<BorrowBooksTable> findAllByUser(Long id, Pageable pageable);

    @Query("select c from BorrowBooksTable c where c.userId=?1 and c.status=?2")
    public List<BorrowBooksTable> findAllByUserAndStatus(Long id, Integer status);

    @Query("select c from BorrowBooksTable c where c.bookId=?1 and c.status=?2")
    public BorrowBooksTable findoneByBookIdAndStatus(Long id,Integer status);

}
