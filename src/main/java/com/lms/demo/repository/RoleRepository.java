package com.lms.demo.repository;

import com.lms.demo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  RoleRepository extends JpaRepository<Role,Long> {
    Role findOneByName(String name);
}
