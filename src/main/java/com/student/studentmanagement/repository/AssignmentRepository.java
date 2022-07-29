package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Assignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByIsActive(Boolean isActive);
    List<Assignment> findAllByStudentAndIsActive(Student student, Boolean isActive);
}
