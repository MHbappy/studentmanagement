package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Grade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByIsActive(Boolean isActive);

}
