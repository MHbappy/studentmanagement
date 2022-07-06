package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Departments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Departments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {}
