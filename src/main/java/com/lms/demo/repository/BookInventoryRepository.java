package com.lms.demo.repository;


import com.lms.demo.domain.BookInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookInventoryRepository extends JpaRepository<BookInventory,Long>,JpaSpecificationExecutor<BookInventory> {
    @Modifying
    @Query("update BookInventory c set c.bookBorrowQuantity = c.bookBorrowQuantity+1 where c.bookName = ?1")
    public int borrowBook(String bookName);

    @Modifying
    @Query("update BookInventory c set c.bookTotalQuantity = c.bookTotalQuantity+1 where c.bookName = ?1")
    public int addBook(String bookName);

    public BookInventory findOneByBookName(String bookName);

    @Query("select c from BookInventory c where c.status=?1")
    public Page<BookInventory> findAll(Integer s, Pageable pageable);


}
