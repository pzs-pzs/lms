package com.lms.demo.repository;

import com.lms.demo.domain.Fine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FineRepository extends JpaRepository<Fine,Long> {
    @Query("select c from Fine c where c.status=?1 and c.uId=?2")
    public Page<Fine> findAllByUserId(int status, Long id, Pageable pageable);

}
