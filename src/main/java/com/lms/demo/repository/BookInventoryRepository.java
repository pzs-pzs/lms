package com.lms.demo.repository;


import com.lms.demo.domain.Book;
import com.lms.demo.domain.BookInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;

public interface BookInventoryRepository extends JpaRepository<BookInventory,Long>,JpaSpecificationExecutor<BookInventory> {
    @Modifying
    @Query("update BookInventory c set c.bookBorrowQuantity = c.bookBorrowQuantity+1 where c.bookName = ?1")
    public void borrowBook(String bookName);

    @Modifying
    @Query("update BookInventory c set c.bookTotalQuantity = c.bookTotalQuantity+1 where c.bookName = ?1")
    public void addBook(String bookName);

    public BookInventory findOneByBookName(String bookName);


}
