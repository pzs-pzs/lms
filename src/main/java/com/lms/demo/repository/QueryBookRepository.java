package com.lms.demo.repository;

import com.lms.demo.query.QueryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;


public interface QueryBookRepository extends SolrCrudRepository<QueryBook,String> {
    @Query(value = "name:*?0*")
    Page<QueryBook> findByNameContaining(String name, Pageable pageable);
}
