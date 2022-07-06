package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Role} entity.
 */
public interface RoleRepository extends JpaRepository<Role, String> {}
