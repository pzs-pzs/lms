package com.lms.demo.repository;


import com.lms.demo.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book,Long>,JpaSpecificationExecutor<Book>{
    @Query("select c from Book c where c.status=?1 ")
    public Page<Book> findAll(Integer status,Pageable pageable);

    @Query("select c from Book c where c.status=?1 and c.id =?2")
    public Book findOne(Integer status,Long id);

}
