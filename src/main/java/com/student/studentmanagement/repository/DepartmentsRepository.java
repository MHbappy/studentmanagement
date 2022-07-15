package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Departments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data SQL repository for the Departments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
    List<Departments> findAllByIsActive(Boolean isActive);
    Boolean existsByNameAndIsActive(String name, Boolean isActive);
}
