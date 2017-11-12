package com.lms.demo.repository;

import com.lms.demo.domain.User;
import com.lms.demo.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
