package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Departments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Departments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
    List<Departments> findAllByIsActive(Boolean isActive);

}
